<#include "header.ftl"/>

<#macro content>
    <div class="container">
        <form class="pt-4 pb-4" enctype="multipart/form-data" method="post">
            <div class="form-group">
                <label for="firstname">Имя</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="имя">
            </div>
            <div class="form-group">
                <label for="lastname">Фамилия</label>
                <input type="text" class="form-control" id="surname" name="surname" placeholder="фамилия">
            </div>
            <div class="form-group">
                <label for="email">Адрес электронной почты</label>
                <#if user_exists == true>
                    <input type="email" class="form-control is-invalid" id="email" name="email" placeholder="email@domain.com">
                <#else>
                    <input type="email" class="form-control" id="email" name="email" placeholder="email@domain.com">
                </#if>
                <div class="invalid-feedback">Пользователь с таким адресом уже зарегистрирован</div>
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <#if correct_password == true>
                    <input type="password" class="form-control" id="password" name="password" placeholder="* * * * * * * *">
                <#else>
                    <input type="password" class="form-control is-invalid" id="password" name="password" placeholder="* * * * * * * *">
                </#if>
                <div class="invalid-feedback">Пароли не совпадают</div>
            </div>
            <div class="form-group">
                <label for="passwordconfirm">Повторите пароль</label>
                <#if correct_password == true>
                    <input type="password" class="form-control" id="repeatpassword" name="repeatpassword" placeholder="* * * * * * * *">
                <#else>
                    <input type="password" class="form-control is-invalid" id="repeatpassword" name="repeatpassword" placeholder="* * * * * * * *">
                </#if>
                <div class="invalid-feedback">Пароли не совпадают</div>
            </div>

            <div class="form-group">
                <label for="avatar">Аватар</label>
                <input type="file" class="form-control" id="avatar" name="avatar">
            </div>

            <div class="form-group">
                <button type="submit" class="mt-2 btn btn-success">Зарегистрироваться</button>
            </div>
        </form>
    </div>
</#macro>

<#macro title>Регистрация</#macro>

<@main/>