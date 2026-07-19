<html>
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpRequestAttachment" -->
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
    <script>hljs.highlightAll();</script>

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
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="card mb-3">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Request Information</h5>
        </div>
        <div class="card-body">
            <pre class="mb-0"><code><#if data.method??>${data.method}<#else>GET</#if>: <#if data.url??>${data.url}<#else>Unknown</#if></code></pre>
        </div>
    </div>

    <#if data.body??>
    <div class="card mb-3">
        <div class="card-header">
            <h5 class="mb-0">Body</h5>
        </div>
        <div class="card-body">
            <pre><code class="language-json">${data.body}</code></pre>
        </div>
    </div>
    </#if>

    <#if (data.headers)?has_content>
    <div class="card mb-3">
        <div class="card-header">
            <h5 class="mb-0">Headers</h5>
        </div>
        <div class="card-body">
            <#list data.headers as names, value>
            <div class="mb-1">
                <pre class="mb-0"><code><b>${names}</b>: ${value}</code></pre>
            </div>
            </#list>
        </div>
    </div>
    </#if>

    <#if (data.cookies)?has_content>
    <div class="card mb-3">
        <div class="card-header">
            <h5 class="mb-0">Cookies</h5>
        </div>
        <div class="card-body">
            <#list data.cookies as names, value>
            <div class="mb-1">
                <pre class="mb-0"><code><b>${names!}</b>: ${value!}</code></pre>
            </div>
            </#list>
        </div>
    </div>
    </#if>

    <#if data.curl??>
    <div class="card">
        <div class="card-header">
            <h5 class="mb-0">Curl</h5>
        </div>
        <div class="card-body">
            <pre><code class="language-bash">${data.curl}</code></pre>
        </div>
    </div>
    </#if>
</div>
</body>
</html>