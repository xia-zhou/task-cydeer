<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div data-ng-controller="userLoginCtrl" class="middle-box text-center animated fadeInDown">
    <div>
        <div>
            <h3>
          	  ${code} [ ${message} ]
			</h3>
			<div style="display:none">
				${exception}
			</div>
        </div>
        <p class="m-t">
            <small>Alex © 2015</small>
        </p>
    </div>
</div>
