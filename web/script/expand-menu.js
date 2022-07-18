/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


const menus = document.querySelectorAll('.expand-menu');

menus.forEach((menu, index) => {
    const list = menu.querySelector('.list');

    list.style.maxHeight = list.children.length * 49 + 'px';

    menu.querySelector('.option').addEventListener('click', (e) => {
        list.style.maxHeight = list.offsetHeight === 0 ? list.children.length * 49 + 'px' : 0;
    });
});