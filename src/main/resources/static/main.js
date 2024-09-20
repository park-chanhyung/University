
function regist(event){
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    fetch(form.action,{
        method : 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            if(data.success){
                window.location.reload();
            }
        })
        .catch(error =>{
            alert(error.message);
        });
}
function handleCancellation(event) {
    event.preventDefault();
    if (!confirm('정말로 수강을 취소하시겠습니까?')) {
        return;
    }

    const form = event.target;
    const formData = new FormData(form);

    fetch(form.action, {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            if (data.success) {
                // 성공 시 페이지 새로고침
                window.location.reload();
            }
        })
        .catch(error => {
            alert('오류가 발생했습니다: ' + error.message);
        });
}