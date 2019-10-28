<#include "header.ftl"/>

<#macro content>
    <div class="container">

        <h1 class="h1 mt-3 mb-3">${article.getTitle()}</h1>
        <div class="description">
            ${@article.getText()}
        </div>
        <div class="mt-3 mb-3">
            <a href="correct.html">Suggest revision</a>
        </div>
        <div class="row">
            <div class="col">
                <a href="user.html">${author.getName()} ${author.getSurname()}</a>
            </div>
            <div class="col">
                <div class="like-dislike text-right">
                    <a href="#" class="btn-sm btn-success">+ ${likes}</a>
                    <a href="#" class="btn-sm btn-danger">- ${dislikes}</a>
                </div>
            </div>
        </div>

        <div class="comment">
            <form class="pt-5" method="post">

                <div class="form-group">
                    <label for="text">Comment</label>
                    <textarea class="form-control" id="text" rows="3" name="text"></textarea>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-success">Comment</button>
                </div>
            </form>

            <div class="comment-list">
                <div class="comment-list-item pt-3 pb-3">
                    <#list comments as comment>
                        <#if sender == null>
                            <div class="comment-list-username"><span class="text-success pb-2 d-block">Anonymous</span></div>
                        <#else>
                            <div class="comment-list-username"><span class="text-success pb-2 d-block">${sender.getName()} ${sender.getSurname()}</span></div>
                        </#if>

                        <div class="comment-list-text"><p></p></div>
                    </#list>
            </div>
        </div>
    </div>
</#macro>
