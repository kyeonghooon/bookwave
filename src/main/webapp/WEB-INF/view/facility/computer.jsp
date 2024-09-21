<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/facility/computer.css">

<div class="container">
	<div class="title--section text-center mb-4">
		<h1>컴퓨터 예약</h1>
	</div>
	<div class="color--legend d-flex justify-content-center mb-4">
		<div class="legend--item mr-4">
			<span class="legend--color available--color"></span> 예약 가능
		</div>
		<div class="legend--item mr-4">
			<span class="legend--color unavailable--color"></span> 예약 불가능
		</div>
	</div>
	<div class="computer--layout ml-auto mr-auto">
		<!-- 가로로 5개씩 3줄 배치 -->
		<div class="d-flex flex-column">
			<div class="computer--grid">
				<c:forEach var="computer" items="${computerList}" varStatus="status">
					<c:if test="${status.index < 15}">
						<div class="computer">
							<button class="computer--btn ${computer.status ? 'computer--available' : 'computer--unavailable'}" data-id="${computer.id}" ${computer.status ? '' : 'disabled'}>${computer.id}</button>
						</div>
						<c:if test="${(status.index + 1) % 5 == 0}">
			</div>
			<div class="computer--grid"></div>
			<div class="computer--grid">
				</c:if>
				</c:if>
				</c:forEach>
			</div>
		</div>

		<!-- 오른쪽 세로로 5대 배치 -->
		<div class="computer--column"></div>
		<div class="computer--column">
			<c:forEach var="computer" items="${computerList}" varStatus="status">
				<c:if test="${status.index >= 15}">
					<div class="computer">
						<button class="computer--btn ${computer.status ? 'computer--available' : 'computer--unavailable'}" data-id="${computer.id}" ${computer.status ? '' : 'disabled'}>${computer.id}</button>
					</div>
				</c:if>
			</c:forEach>
		</div>

		<!-- 왼쪽 아래 문 표시 -->
		<div class="door">문</div>
	</div>
</div>
<script src="/js/facility/computer.js"></script>
<%@ include file="../layout/footer.jsp"%>