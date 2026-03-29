<style>
    .done-text { @apply line-through text-slate-400; }
    .done-row { @apply bg-slate-50 opacity-70 transition-all; }
</style>

<main class="page-wrap">
    <div class="content-card card-pad border-l-8 border-brand-500 mb-10 shadow-sm">
        <h2 class="text-2xl font-black mb-2">${dto.title}</h2>
        <p class="text-slate-600 mb-6 text-lg">${dto.content}</p>
        <div class="flex gap-4">
            <div class="stats-box flex-1"><p class="text-xs text-slate-400">기간</p><p class="font-bold font-mono">${dto.startDate} ~ ${dto.endDate}</p></div>
        </div>
    </div>

    <div class="content-card card-pad mb-10">
        <h3 class="panel-title">학습 캘린더</h3>
        <div class="grid grid-cols-7 gap-px bg-slate-200 border border-slate-200 rounded-xl overflow-hidden">
            <c:forEach var="day" items="일,월,화,수,목,금,토"><div class="bg-slate-50 py-2 text-center text-[10px] font-bold text-slate-400">${day}</div></c:forEach>
            <c:forEach begin="1" end="31" var="d">
                <div class="bg-white min-h-[60px] p-2 text-xs font-semibold hover:bg-brand-50 transition-colors">
                    ${d}
                    <c:if test="${d >= 15 && d <= 20}"> <div class="mt-1 h-1.5 w-full bg-brand-500 rounded-full"></div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="content-card overflow-hidden">
        <div class="card-pad border-b flex justify-between items-center bg-slate-50/50">
            <h4 class="font-bold text-lg">스터디 할 일 (To-Do)</h4>
            <button class="btn btn-brand btn-sm" onclick="location.href='/teamtwo/study/todo-add.do?seq=${dto.seq}';">할 일 추가</button>
        </div>
        <table class="table w-full">
            <tbody>
                <c:forEach items="${todolist}" var="todoDto">
                    <tr class="${todoDto.status == 1 ? 'done-row' : ''} transition-all">
                        <td class="w-16 text-center">
                            <input type="checkbox" class="checkbox checkbox-primary todo-ajax" 
                                   data-seq="${todoDto.seq}" ${todoDto.status == 1 ? 'checked' : ''}>
                        </td>
                        <td class="py-4">
                            <div class="font-bold text-slate-700 todo-title ${todoDto.status == 1 ? 'done-text' : ''}">${todoDto.title}</div>
                            <div class="text-xs text-slate-400 todo-content ${todoDto.status == 1 ? 'done-text' : ''}">${todoDto.content}</div>
                        </td>
                        <td class="w-40 text-right pr-6">
                            <div class="join">
                                <button class="btn btn-ghost btn-xs text-warning join-item" onclick="location.href='/teamtwo/study/todo-edit.do?seq=${todoDto.seq}&scheduleSeq=${dto.seq}';">수정</button>
                                <button class="btn btn-ghost btn-xs text-error join-item" onclick="location.href='/teamtwo/study/todo-del.do?seq=${todoDto.seq}&scheduleSeq=${dto.seq}';">삭제</button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</main>

<script>
// To-Do 상태 변경 AJAX 로직
document.querySelectorAll('.todo-ajax').forEach(ck => {
    ck.addEventListener('change', function() {
        const row = this.closest('tr'), seq = this.dataset.seq, status = this.checked ? 1 : 0;
        const texts = row.querySelectorAll('.todo-title, .todo-content');

        fetch('/teamtwo/study/todo-status.do', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `seq=\${seq}&status=\${status}`
        })
        .then(res => res.json())
        .then(data => {
            if (data.result === 1) {
                row.classList.toggle('done-row', this.checked);
                texts.forEach(el => el.classList.toggle('done-text', this.checked));
            } else { alert('상태 변경 실패'); this.checked = !this.checked; }
        });
    });
});
</script>