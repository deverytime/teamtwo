<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Devery Time</title>
  <%@ include file="/WEB-INF/views/inc/asset.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/views/inc/header.jsp" %>
  
  <div class="page-wrap">
    <section class="demo-block">
        <div class="flex items-center justify-between py-3 px-5">
            <h2 class="section-title">학습계획 목록</h2>
            <button class="btn btn-outline btn-success rounded-4xl"
                    onclick="location.href='/teamtwo/plan/select-type.do'">
              학습계획 등록
            </button>
        </div>
        <div class="content-card overflow-hidden border-3 border-indigo-300!">
            <div class="card-pad flex flex-col gap-2 md:flex-row md:items-center md:justify-between">
            <div>
                <h3 class="text-xl font-serif text-indigo-400">학습계획을 조회하고, 진행률·상태 등을 확인해 보세요</h3>
            </div>
              <form action="/teamtwo/plan/list.do" method="get"
                class="flex flex-col sm:flex-row gap-2 md:justify-end">
      
              <select name="type" class="select select-bordered bg-white w-full sm:w-32">
                  <option value="">전체 상태</option>
                  <option value="진행중" ${param.type == '진행중' ? 'selected' : ''}>진행중</option>
                  <option value="완료" ${param.type == '완료' ? 'selected' : ''}>완료</option>
                  <option value="미완료" ${param.type == '미완료' ? 'selected' : ''}>미완료</option>
              </select>
      
              <input
                  type="text"
                  name="title"
                  value="${param.title}"
                  placeholder="학습계획명 검색"
                  class="input input-bordered bg-white w-full sm:flex-1"
              />
              <button type="submit" class="btn btn-brand shrink-0">검색</button>
          </form>
          </div>

            <div class="px-6">
              <div class="overflow-x-auto">
              <table class="table">
                <thead>
                <tr>
                  <th>학습계획명</th>
                  <th>진행률</th>
                  <th>설명</th>
                  <th>완료여부</th>
                  <th>시작일</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="dto">
                      
                      <tr onclick="location.href='/plan/detail.do?seq=101'" style="cursor:pointer;"
                        class="hover:bg-slate-100 cursor-pointer">
                        <td>${dto.title}</td>
<%--                         <td>${dto.progress}%</td> --%>
                        <td>80%</td>
                        <td>${dto.description}</td>
                        <td><span class="badge badge-success badge-outline">${dto.progressStatus}</span></td>
                        <td>${dto.regDate}</td>
                      </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
                
                <div class="card-pad border-t border-slate-200 flex justify-center items-center">
                  <div class="join">
                    <div id="pagebar">${pagebar}</div>
                  </div>
                </div>
                
            </div>
        </div>
    </section>

</div>
</body>
</html>