function removeSpaces() {
    var keywordInput = document.getElementById('keyword');
    keywordInput.value = keywordInput.value.replace(/\s+/g, '');
    return true; // 폼 제출을 계속 진행
}