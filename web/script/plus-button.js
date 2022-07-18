/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function () {
    const plusContainer = document.querySelectorAll('.plus');

    plusContainer.forEach((plus) => {
        const plusBtn = plus.querySelector('.right');
        const minusBtn = plus.querySelector('.left');
        const input = plus.querySelector('input');

        const max = parseInt(input.getAttribute('max')) || 99;
        const min = parseInt(input.getAttribute('min')) || 0;

        if (parseInt(input.value) < min)
            input.value = min;

        plusBtn.onclick = (e) => {
            input.value = parseInt(input.value) + 1;
            if (parseInt(input.value) > max) {
                input.value = max;
            }
        };

        minusBtn.onclick = (e) => {
            if (parseInt(input.value) > min) {
                input.value = parseInt(input.value) - 1;
            }
        };

        input.addEventListener('input', (e) => {
            input.value = parseInt(input.value) || 0;
            if (parseInt(input.value) < min) {
                input.value = min;
            }
            if (parseInt(input.value) > max) {
                input.value = max;
            }
        });
    });

    window.addEventListener('scroll', (e) => {
        let currentPos = (document.documentElement.scrollTop) / (document.documentElement.scrollHeight - window.innerHeight);
        document.getElementById('scroll-bar').style.width = `${currentPos * 100}%`;
    });
})();