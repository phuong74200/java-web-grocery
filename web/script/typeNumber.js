/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


;
(function () {
    const numbs = document.querySelectorAll('[data-type="number"]');
    const float = document.querySelectorAll('[data-type="float"]');

    numbs.forEach((numb) => {
        const min = parseInt(numb.getAttribute('min')) || 0;
        const max = parseInt(numb.getAttribute('max')) || 365;

        if (parseInt(numb.value) < min)
            numb.value = min;

        numb.addEventListener('input', (e) => {
            const n = parseInt(numb.value);

            numb.value = n || min;

            if (n > max)
                numb.value = max;
            if (n < min)
                numb.value = min;
        });
    });

    float.forEach((input) => {
        const min = parseFloat(input.getAttribute('min')) || 0;
        const max = parseFloat(input.getAttribute('max')) || 99;
        const reg = /(^[0-9]{0,2}$)|(^[0-9]{0,2}\.[0-9]{0,5}$)/;

        input.addEventListener('input', (e) => {
            const n = parseFloat(input.value);

            if (!reg.test(input.value)) {
                input.value = n || min;
            }

            if (n > max)
                input.value = max || 0;
            if (n < min)
                input.value = min || 0;
        });
    });
})();