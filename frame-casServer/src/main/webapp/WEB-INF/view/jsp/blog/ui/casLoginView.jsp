<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<!DOCTYPE html>

<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
<meta charset="UTF-8" />

<title>CAS 单点登录</title>
<link rel="stylesheet" href="${ctx}/bootstrap/css/bootstrap.min.css" />
<style type="text/css">
body {
	background-color: #eee;
	padding-bottom: 40px;
	padding-top: 40px;
}

.form-signin {
	margin: 0 auto;
	max-width: 330px;
	padding: 15px 15px 30px 15px;
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin .checkbox {
	font-weight: normal;
}

.form-signin .form-control {
	box-sizing: border-box;
	font-size: 16px;
	height: 38px;
	line-height: 38px;
	margin-bottom: 20px;
	padding: 4px 8px;
	position: relative;
}

.form-signin .form-control:focus {
	z-index: 2;
}

.form-signin input[type="email"] {
	border-bottom-left-radius: 0;
	border-bottom-right-radius: 0;
	margin-bottom: -1px;
}

.form-signin input[type="password"] {
	border-top-left-radius: 0;
	border-top-right-radius: 0;
	margin-bottom: 20px;
}

.form-signin input[type="captcha"] {
	border-top-left-radius: 0;
	border-top-right-radius: 0;
	margin-bottom: 20px;
}

.form-control.captcha {
	width: 150px;
	float: left;
	margin-right: 10px;
}

.btn-lg {
	height: 36px;
	line-height: 36px;
	padding: 0;
}
.login-box-body, .register-box-body {
	background: #fff;
	padding: 20px;
	border-top: 0;
	color: #666;
	width: 37%;
	align-content: center;
	margin: 0 auto;
	margin-top: 22%;
}
body{
	min-width: 100%;
	min-height: 100%;
	background: black url(images/login-bg.jpg) no-repeat fixed top;
}
#msg{
	color: red;
	width: 300px;
	top: 70px;
	border: 1px solid;
	background: rgba(255, 0, 0, 0.14);
	text-indent: 7px;
	margin: 10px 0 20px;
}
.title{
	font-size: 25px;
	font-weight: bold;
	padding-bottom: 25px;
	text-align: center;
}

#kaptcha {
	color: red;
	font-size: 17px;
	font-weight: bold;
	margin-bottom: 14px;
	margin-top: 5px;
}
</style>
</head>


<div class="container">

	<div class="login-box-body">
		<form:form cssClass="form-signin" method="post" id="fm1"
				   commandName="${commandName}" htmlEscape="true">
			<h2 class="form-signin-heading title">hxyFrame系统</h2>
			<form:errors path="*" id="msg" cssClass="errors" element="div"
						 htmlEscape="false" />
			<input class="form-control" id="username" name="username" placeholder="帐号"
				   required="" autofocus="" type="username">
			<input type="hidden" name="lt" value="${loginTicket}" />
			<input type="hidden" name="execution" value="${flowExecutionKey}" />
			<input type="hidden" name="_eventId" value="submit" />


			<input class="form-control" id="password" name="password" placeholder="密码"
				   required="" type="password">
			<%--<div class="checkbox">
              <label>
                <input value="remember-me" type="checkbox"> Remember me
              </label>
            </div>--%>
			<input type="text" class="form-control captcha" id="captcha"
				   name="captcha" placeholder="验证码" tabindex="3" type="captcha">
			<img align="absmiddle" id="kaptcha" src="kaptcha.jpg" >
			<a id="changeCaptcha" href="javascript:void(0);">换一张</a>
			<button class="btn btn-lg btn-primary btn-block" style="background-color: #00a65a;border-color: #008d4c;" type="submit">登
				录</button>
		</form:form>
	</div>

</div>

<script src="${ctx}/bootstrap/js/jquery-1.11.0.min.js"></script>
<script src="${ctx}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#changeCaptcha').click(     
			        function() {     
			           $("#kaptcha").hide().attr('src','kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();     
			    });
	});


</script>
</body>
</html>
