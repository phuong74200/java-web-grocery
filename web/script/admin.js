/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function () {

    const img = document.getElementById('previewImg');

    function dropHandler(ev) {
        console.log('File(s) dropped');
        imgLabel.classList.remove('over');

        // Prevent default behavior (Prevent file from being opened)
        ev.preventDefault();

        document.getElementById('imageDropZone').files = ev.dataTransfer.files;

        if (ev.dataTransfer.items) {
            // Use DataTransferItemList interface to access the file(s)
            for (var i = 0; i < ev.dataTransfer.items.length; i++) {
                // If dropped items aren't files, reject them
                if (ev.dataTransfer.items[i].kind === 'file'
                        && (ev.dataTransfer.items[i].type === 'image/png' || ev.dataTransfer.items[i].type === 'image/jpeg')) {
                    var file = ev.dataTransfer.items[i].getAsFile();

//                    const reader = new FileReader();
//
//                    reader.addEventListener("load", function () {
//
//                    }, false);
//
//                    reader.readAsDataURL(file);

                    const blobURL = URL.createObjectURL(file);

                    img.src = blobURL;
                    imgLabel.querySelector('img').src = blobURL;
                }
            }
        } else {
            // Use DataTransfer interface to access the file(s)
            for (var i = 0; i < ev.dataTransfer.files.length; i++) {
                console.log('... file[' + i + '].name = ' + ev.dataTransfer.files[i].name);
            }
        }
    }

    const imgLabel = document.getElementById('imageDropLabel');

    const dragOverHandler = (ev) => {
        imgLabel.classList.add('over');
        ev.preventDefault();
    };

    const dragLeave = () => {
        imgLabel.classList.remove('over');
    };

    imgLabel.addEventListener('dragover', dragOverHandler);
    imgLabel.addEventListener('drop', dropHandler);
    imgLabel.addEventListener('dragleave', dragLeave);

    const handleFiles = (e) => {
        const fileList = e.target.files;

        for (let i = 0; i < fileList.length; i++) {
            // If dropped items aren't files, reject them
            if (fileList[i].type === 'image/png' || fileList[i].type === 'image/jpeg') {
                let file = fileList[i];

//                const reader = new FileReader();
//
//                reader.addEventListener("load", function () {
//                    img.src = reader.result;
//                }, false);
//
//                reader.readAsDataURL(file);

                const blobURL = URL.createObjectURL(file);
                img.src = blobURL;
                imgLabel.querySelector('img').src = blobURL;
            }
        }
    };

    document.getElementById('imageDropZone').addEventListener('change', handleFiles, false);

    const productTitle = document.getElementById('productTitle');
    const previewTitle = document.getElementById('previewTitle');

    const discountInput = document.getElementById('discountInput');
    const discount = document.getElementById('discount');

    const priceInput = document.getElementById('priceInput');
    const pricePreview = document.getElementById('pricePreview');

    const calDiscount = () => {
        const full = parseFloat(priceInput.value);
//        const discountPercent = parseInt(discountInput.value);
//        const disc = full - (full * (discountPercent / 100));
        return full.toFixed(2);
    };

    productTitle.addEventListener('keyup', (e) => {
        previewTitle.textContent = productTitle.value || "Product Title";
    });

    priceInput.addEventListener('keyup', (e) => {
        // pricePreview.querySelector('del').textContent = parseFloat(priceInput.value).toFixed(2);
        pricePreview.querySelector('bdi').textContent = '$' + (calDiscount() || 14);
    });

//    discountInput.addEventListener('keyup', (e) => {
//        if (parseInt(discountInput.value) === 0) {
//            pricePreview.querySelector('del').style.display = 'none';
//            discount.style.display = 'none';
//        } else {
//            pricePreview.querySelector('del').style.display = 'inline';
//            discount.style.display = 'block';
//        }
//        discount.textContent = (discountInput.value || 0) + '%';
//        pricePreview.querySelector('bdi').textContent = '$' + (calDiscount() || 12);
//        pricePreview.querySelector('del').textContent = parseFloat(priceInput.value).toFixed(2);
//    });

    window.addEventListener('scroll', (e) => {
        let currentPos = (document.documentElement.scrollTop) / (document.documentElement.scrollHeight - window.innerHeight);
        document.getElementById('scroll-bar').style.width = `${currentPos * 100}%`;
    });
})();