<html>
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpResponseAttachment" -->
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          crossorigin="anonymous" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"></script>

    <!-- Highlight.js 11.x -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/bash.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/json.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/xml.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/plaintext.min.js"></script>

    <script>
    hljs.configure({
        ignoreUnescapedHTML: true,  // Игнорировать неэкранированный HTML
        throwUnescapedHTML: false   // Не выбрасывать ошибку при неэкранированном HTML
    });
    hljs.highlightAll();
    </script>

    <style>
        pre {
            white-space: pre-wrap;
            background-color: #f8f9fa;
            padding: 1rem;
            border-radius: 0.375rem;
            border: 1px solid #dee2e6;
        }
        body {
            padding: 1rem;
        }
        .status-code {
            font-weight: bold;
            padding: 0.25rem 0.5rem;
            border-radius: 0.25rem;
            display: inline-block;
        }
        .status-2xx { background-color: #d1e7dd; color: #0f5132; }
        .status-3xx { background-color: #fff3cd; color: #856404; }
        .status-4xx { background-color: #f8d7da; color: #721c24; }
        .status-5xx { background-color: #f8d7da; color: #721c24; }
        .html-warning {
            background-color: #fff3cd;
            border: 1px solid #ffc107;
            border-radius: 0.375rem;
            padding: 0.75rem;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <!-- Блок информации об ответе -->
    <div class="card mb-3">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Response Information</h5>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <h6>Status Code</h6>
                <#if data.responseCode??>
                    <#assign statusClass>
                        <#if data.responseCode?starts_with("2")>status-2xx
                        <#elseif data.responseCode?starts_with("3")>status-3xx
                        <#elseif data.responseCode?starts_with("4")>status-4xx
                        <#elseif data.responseCode?starts_with("5")>status-5xx
                        <#else>status-other</#if>
                    </#assign>
                    <span class="status-code ${statusClass}">${data.responseCode}</span>
                <#else>
                    <span class="text-muted">Unknown</span>
                </#if>
            </div>

            <#if data.url??>
            <div>
                <h6>URL</h6>
                <pre class="mb-0"><code>${data.url}</code></pre>
            </div>
            </#if>
        </div>
    </div>

    <!-- Заголовки -->
    <#if (data.headers)?has_content>
    <div class="card mb-3">
        <div class="card-header">
            <h5 class="mb-0">Response Headers</h5>
        </div>
        <div class="card-body">
            <#list data.headers as name, value>
            <div class="mb-1">
                <pre class="mb-0"><code><b>${name}</b>: ${value}</code></pre>
            </div>
            </#list>
        </div>
    </div>
    </#if>

    <!-- Тело ответа - КРИТИЧЕСКИЙ ИСПРАВЛЕННЫЙ БЛОК -->
    <#if data.body??>
    <div class="card mb-3">
        <div class="card-header">
            <h5 class="mb-0">Response Body</h5>
        </div>
        <div class="card-body">
            <#-- 1. Получаем Content-Type из заголовков -->
            <#assign contentType = "">
            <#list data.headers as name, value>
                <#if name?lower_case == "content-type">
                    <#assign contentType = value?lower_case>
                    <#break>
                </#if>
            </#list>

            <#-- 2. Определяем, содержит ли тело HTML-теги -->
            <#assign bodyText = data.body?trim>
            <#assign hasHtmlTags = bodyText?contains("<") && bodyText?contains(">")>
            <#assign hasScriptTags = bodyText?contains("<script") || bodyText?contains("</script>")>
            <#assign hasDangerousTags = hasScriptTags || bodyText?contains("<iframe") || bodyText?contains("<img") || bodyText?contains("<svg")>

            <#-- 3. Показываем предупреждение для HTML -->
            <#if hasHtmlTags && contentType?contains("html")>
                <div class="html-warning">
                    ⚠️ <strong>Внимание:</strong> Ответ содержит HTML.
                    Для безопасности теги отображаются как текст.
                    <#if hasDangerousTags>
                        <br><small class="text-danger">Обнаружены потенциально опасные теги!</small>
                    </#if>
                </div>
            </#if>

            <#-- 4. Выбираем стратегию отображения -->
            <#assign bodyClass = "">
            <#assign displayBody = "">

            <#if contentType?contains("json") || (bodyText?starts_with("{") && bodyText?ends_with("}")) || (bodyText?starts_with("[") && bodyText?ends_with("]"))>
                <#-- JSON: отображаем как есть (highlight.js безопасно обработает) -->
                <#assign bodyClass = "language-json">
                <#assign displayBody = data.body>
            <#elseif contentType?contains("xml") || (hasHtmlTags && (bodyText?starts_with("<?xml") || bodyText?contains("<xml")))>
                <#-- XML: экранируем для безопасности -->
                <#assign bodyClass = "language-xml">
                <#assign displayBody = data.body?html>
            <#elseif contentType?contains("html") || (hasHtmlTags && !bodyText?starts_with("{") && !bodyText?starts_with("["))>
                <#-- HTML: ВСЕГДА экранируем! -->
                <#assign bodyClass = "language-html">
                <#assign displayBody = data.body?html>
            <#else>
                <#-- Все остальное: отображаем как есть -->
                <#assign bodyClass = "language-plaintext">
                <#assign displayBody = data.body>
            </#if>

            <#-- 5. Отображаем тело -->
            <pre><code class="${bodyClass}">${displayBody}</code></pre>

            <#-- 6. Опционально: кнопка для просмотра HTML в iframe -->
            <#if hasHtmlTags && contentType?contains("html")>
                <div class="mt-3">
                    <button type="button" class="btn btn-sm btn-outline-secondary"
                            data-bs-toggle="collapse" data-bs-target="#htmlPreview">
                        👁️ Показать HTML-превью (в изоляции)
                    </button>
                    <div id="htmlPreview" class="collapse mt-2">
                        <div class="alert alert-warning small">
                            ⚠️ HTML выполняется в изолированном iframe.
                        </div>
                            <iframe
                                srcdoc="${data.body?html?replace('"', '&quot;')}"
                                sandbox="allow-same-origin"
                                style="width: 100%; height: 300px; border: 1px solid #ccc;">
                            </iframe>
                    </div>
                </div>
            </#if>
        </div>
    </div>
    </#if>

    <!-- Куки -->
    <#if (data.cookies)?has_content>
    <div class="card">
        <div class="card-header">
            <h5 class="mb-0">Cookies</h5>
        </div>
        <div class="card-body">
            <#list data.cookies as name, value>
            <div class="mb-1">
                <pre class="mb-0"><code><b>${name!}</b>: ${value!}</code></pre>
            </div>
            </#list>
        </div>
    </div>
    </#if>
</div>
</body>
</html>