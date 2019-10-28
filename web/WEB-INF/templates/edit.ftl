<#include "header.ftl"/>

<#macro content>
    <div class="container">
        <form class="pt-4 pb-4" enctype="multipart/form-data" method="post">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${user.getName()}">
            </div>
            <div class="form-group">
                <label for="surname">Surname</label>
                <input type="text" class="form-control" id="surname" name="surname" value="${user.getSurname()}">
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.getEmail()}">
            </div>

            <div class="form-group">
                <label for="avatar">Avatar</label>
                <input type="file" class="form-control" id="avatar" name="avatar">
            </div>

            <div class="form-group">
                <button type="submit" class="mt-2 btn btn-success">Edit</button>
            </div>
        </form>
    </div>
</#macro>

<#macro title>Editing</#macro>

<@main/>