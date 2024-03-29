<#include "header.ftl"/>

<#macro content>
    <div class="container">
        <div class="corrections pt-3 pb-4">
            <div class="correction-element pt-5 pb-5 border border-right-0 border-left-0">
                <#list revisions as revision>
                    <div id="${revision.getId()}">
                        <div class="form-group">
                            <textarea class="correction-text form-control" rows="7">
                                ${revision.getText()}
                            </textarea>
                        </div>
                        <div class="correction-description">
                            <p class="text-info">${revision.getDescription()}</p>
                        </div>
                        <div class="correction-link">
                            <h2 class="h3"><a href="/article?article=${revision.getArticleTitle()}" class="text-success">${revision.getArticleTitle()}</a></h2>
                        </div>
                        <div class="correction-buttons">
                            <button class="btn btn-success" onclick="accept('${revision.getId()}')">Accept</button>
                            <button class="btn btn-danger" onclick="reject('${revision.getId()}')">Reject</button>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <script type="application/javascript">
        function accept(revisionId) {
            $.ajax({
                url: "/acceptRevision",
                data:{
                    "revision":revisionId,
                },
                dataType: "json",
                success: function (msg) {
                }
            })
            document.getElementById(revisionId).remove();
        }

        function reject(revisionId) {
            $.ajax({
                url: "/rejectRevision",
                data:{
                    "revision":revisionId,
                },
                dataType: "json",
                success: function (msg) {
                }
            })
            document.getElementById(revisionId).remove();
        }
    </script>
</#macro>

<#macro title>Revisions</#macro>

<@main/>