/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function () {
    const carousels = document.querySelectorAll('.carousel');

    carousels.forEach((carousel) => {
        let counter = 0;

        let isDone = true;

        const interval = parseFloat(carousel.dataset.interval) || 5000;
        const items = carousel.querySelectorAll('.item');
        const slide = carousel.querySelector('.slide');
        const lArrow = carousel.querySelector('.fi-ss-angle-left');
        const rArrow = carousel.querySelector('.fi-ss-angle-right');
        const slideCount = carousel.querySelectorAll('.sc-item');

        slideCount.forEach((div, index) => {
            div.onclick = () => {
                counter = (1 / items.length) * index;
                slider();
            };
        });

        const loop = () => {
            if (isDone) {
                counter += 1 / items.length;
                counter = counter >= 1 ? 0 : counter;
                slider();
            }
        };

        let intervalLoop = setInterval(loop, interval);

        const slider = () => {
            isDone = false;

            clearInterval(intervalLoop);

            const anim = slide.animate([
                {transform: `translateX(-${counter * 100}%)`}
            ], {
                duration: 500,
                fill: 'forwards'
            });

            slideCount.forEach((div, index) => {
                div.style.background = 'rgba(0, 0, 0, 0.2)';
            });
            slideCount[counter / (1 / items.length)].style.background = 'rgba(0, 0, 0, 0.9)';

            anim.onfinish = () => {
                isDone = true;
                intervalLoop = setInterval(loop, interval);
            };
        };

        rArrow.addEventListener('click', (e) => {
            if (isDone) {
                counter += 1 / items.length;
                counter = counter >= 1 ? 0 : counter;
                slider();
            }
        });

        lArrow.addEventListener('click', (e) => {
            if (isDone) {
                counter -= 1 / items.length;
                counter = counter <= 0 ? 0 : counter;
                slider();
            }
        });

        slide.style.width = `${items.length * 100}%`;

        items.forEach((item) => {
            item.style.width = `${100 / items.length}%`;
        });
    });
})();