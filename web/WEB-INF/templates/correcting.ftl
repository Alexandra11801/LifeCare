<#include "header.ftl"/>

<#macro content>
    <div class="container">
        <form class="pt-3 mt-3 pb-4" method="post">

            <input type="text" name="articleId" id="article" style="display: none" value="${articleId}">

            <div class="form-group">
                <label for="text">Text</label>
                <textarea class="form-control" id="text" rows="3" name="text" >${article.getText()}</textarea>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" id="description" rows="2" name="description"></textarea>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-success">Suggest revision</button>
            </div>
        </form>
    </div>
</#macro>

<#macro title>Suggest Revision</#macro>

<@main/>