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
	padding: 15px;
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
	margin-bottom: 10px;
	padding: 4px 8px;
	position: relative;
	width: 280px;
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
	margin-bottom: 10px;
}

.form-signin input[type="captcha"] {
	border-top-left-radius: 0;
	border-top-right-radius: 0;
	margin-bottom: 10px;
}

.form-control.captcha {
	width: 110px;
	float: left;
	margin-right: 10px;
}

.btn-lg {
	height: 36px;
	line-height: 36px;
	padding: 0;
	width: 280px;
}

#kaptcha {
	margin-top: 4px;
}
</style>
</head>



<div class="container">

	<form:form cssClass="form-signin" method="post" id="fm1"
		commandName="${commandName}" htmlEscape="true">
		<form:errors path="*" id="msg" cssClass="errors" element="div"
			htmlEscape="false" />
		<h2 class="form-signin-heading">请登录</h2>
		<input class="form-control" id="username" name="username" placeholder="UserName"
			required="" autofocus="" type="username">
		<input type="hidden" name="lt" value="${loginTicket}" />
		<input type="hidden" name="execution" value="${flowExecutionKey}" />
		<input type="hidden" name="_eventId" value="submit" />


		<input class="form-control" id="password" name="password" placeholder="Password"
			required="" type="password">
		<%--<div class="checkbox">
          <label>
            <input value="remember-me" type="checkbox"> Remember me
          </label>
        </div>--%>
		<input type="text" class="form-control captcha" id="captcha"
			name="captcha" placeholder="captcha" tabindex="3" type="captcha">
		<img align="absmiddle" id="kaptcha" src="kaptcha.jpg" >
		<a id="changeCaptcha" href="javascript:void(0);">换一张</a>
		<button class="btn btn-lg btn-primary btn-block" type="submit">登
			录</button>
	</form:form>

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
