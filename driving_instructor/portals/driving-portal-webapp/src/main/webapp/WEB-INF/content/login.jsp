
<%@ include file="../../includes/_tagLibs.jsp"%>
<!-- Beginning of Main Article -->
<article class="mainArticle">
	<div class="container">
		<div class="homeLogin">
			<div class="align-center">
				<img src="${pageContext.request.contextPath}/images/banner.jpg"
					alt="">
			</div>
			<div class="hero-unit">
				<h1>
					<s:message code="label.indexpage.drivinginstructor"
						text="Driving Instructor School" />
					<br />
					<span><s:message
							code="label.indexpage.findYourFriendyLocalDrivingInstrcutor"
							text="Find your friendly local Driving Instructor" /></span>
				</h1>
			</div>
			<!-- Get instant code -->
			<!--  Include sining form -->
			<%@ include file="./security/_signInForm.jsp"%>
		</div>
	</div>
</article>