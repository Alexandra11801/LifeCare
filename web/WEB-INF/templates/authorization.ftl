<#include "header.ftl"/>

<#macro content>
    <div class="container">
        <form class="pt-4 pb-4" method="post">
            <div class="form-group">
                <label for="login">Email</label>
                 <#if user_exists == true>
                    <input type="email" class="form-control" id="email" name="email" placeholder="email@domain.com">
                 <#else>
                    <input type="email" class="form-control is-invalid" id="email" name="email" placeholder="email@domain.com">
                 </#if>
                 <div class="invalid-feedback">models.User with this email is not registered</div>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <#if correct_password == true>
                    <input type="password" class="form-control" id="password" name="password" placeholder="* * * * * * * *">
                <#else>
                    <input type="password" class="form-control is-invalid" id="password" name="password" placeholder="* * * * * * * *">
                </#if>
                <div class="invalid-feedback">Invalid password</div>
            </div>

            <div class="remember-me-check">
                <input class="form-check-input" type="checkbox" value="" id="defaultCheck1" name="rememberMe">
                <label class="form-check-label" for="defaultCheck1">
                    Check me out
                </label>
            </div>

            <div class="form-group">
                <button type="submit" class="mt-2 btn btn-success">Log in</button>
            </div>
        </form>
    </div>
</#macro>

<#macro title>Authorization</#macro>

<@main/>