document.addEventListener('DOMContentLoaded', function() {
    const getCellValue = (tr, idx) => tr.children[idx].innerText || tr.children[idx].textContent;

    let currentPriorityValue = "";
    let direction = 1; // 1: ascending, -1: descending

    document.querySelectorAll('th.sortable').forEach(th => th.addEventListener('click', function() {
        const table = th.closest('table');
        const tbody = table.querySelector('tbody');
        const colIndex = Array.from(th.parentNode.children).indexOf(th);

        // "PC 광고 경쟁 정도" 칼럼에 대한 처리
        if (colIndex === 8) {
            const values = Array.from(new Set(Array.from(tbody.querySelectorAll('tr')).map(row => getCellValue(row, colIndex)))).sort();

            let currentIndex = values.indexOf(currentPriorityValue);

            currentIndex = (currentIndex + 1) % values.length;
            currentPriorityValue = values[currentIndex];

            const sortedRows = Array.from(tbody.querySelectorAll('tr')).sort((a, b) => {
                const aValue = getCellValue(a, colIndex);
                const bValue = getCellValue(b, colIndex);

                if (aValue === currentPriorityValue) return -1;
                if (bValue === currentPriorityValue) return 1;

                return values.indexOf(aValue) - values.indexOf(bValue);
            });

            sortedRows.forEach(row => tbody.appendChild(row));
        } else {
            // 기존 숫자 정렬 로직
            const getNumericValue = (str) => {
                const numberPattern = /\d+/g;
                let match = str.match(numberPattern);
                return match ? parseInt(match.join('')) : 0;
            };

            const comparer = (a, b) => {
                const aValue = getCellValue(a, colIndex);
                const bValue = getCellValue(b, colIndex);

                const numA = getNumericValue(aValue);
                const numB = getNumericValue(bValue);

                return (numA - numB) * direction;
            };

            const sortedRows = Array.from(tbody.querySelectorAll('tr')).sort(comparer);
            sortedRows.forEach(row => tbody.appendChild(row));

            direction = -direction;  // reverse direction for the next sort
        }
    }));
});
