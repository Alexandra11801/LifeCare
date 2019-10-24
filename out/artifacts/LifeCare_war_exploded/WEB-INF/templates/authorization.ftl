<#include "header.ftl"/>

<#macro content>
    <div class="container">
        <form class="pt-4 pb-4" enctype="multipart/form-data" method="post">
            <div class="form-group">
                <label for="login">Адрес электронной почты</label>
                 <#if user_exists == true>
                    <input type="email" class="form-control" id="email" name="email" placeholder="email@domain.com">
                 <#else>
                    <input type="email" class="form-control is-invalid" id="email" name="email" placeholder="email@domain.com">
                 </#if>
                 <div class="invalid-feedback">Пользователь с таким адресом не зарегистрирован</div>
            </div>

            <div class="form-group">
                <label for="password">Пароль</label>
                <#if correct_password == true>
                    <input type="password" class="form-control" id="password" name="password" placeholder="* * * * * * * *">
                <#else>
                    <input type="password" class="form-control is-invalid" id="password" name="password" placeholder="* * * * * * * *">
                </#if>
                <div class="invalid-feedback">Неверный пароль</div>
            </div>

            <div class="form-group">
                <button type="submit" class="mt-2 btn btn-success">Войти</button>
            </div>
        </form>
    </div>
</#macro>

<#macro title>Авторизация</#macro>

<@main/>