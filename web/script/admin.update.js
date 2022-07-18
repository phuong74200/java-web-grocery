/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


;
(function () {
    const images = document.querySelectorAll('.image');

    images.forEach((image) => {
        const img = image.querySelector('img');
        const input = image.querySelector('input');
        const imgLabel = image.querySelector('label');

        imgLabel.addEventListener('click', (e) => {
            input.click();
        });

        const handleFiles = (e) => {
            const fileList = e.target.files;
            for (let i = 0; i < fileList.length; i++) {
                // If dropped items aren't files, reject them
                if (fileList[i].type === 'image/png' || fileList[i].type === 'image/jpeg') {
                    let file = fileList[i];

                    const blobURL = URL.createObjectURL(file);
                    img.src = blobURL;
                }
            }
        };
        input.addEventListener('change', handleFiles, false);

        function dropHandler(ev) {
            imgLabel.classList.remove('over');

            // Prevent default behavior (Prevent file from being opened)
            ev.preventDefault();

            input.files = ev.dataTransfer.files;

            if (ev.dataTransfer.items) {
                // Use DataTransferItemList interface to access the file(s)
                for (var i = 0; i < ev.dataTransfer.items.length; i++) {
                    // If dropped items aren't files, reject them
                    if (ev.dataTransfer.items[i].kind === 'file'
                            && (ev.dataTransfer.items[i].type === 'image/png' || ev.dataTransfer.items[i].type === 'image/jpeg')) {
                        var file = ev.dataTransfer.items[i].getAsFile();

                        const blobURL = URL.createObjectURL(file);
                        img.src = blobURL;
                    }
                }
            } else {
                // Use DataTransfer interface to access the file(s)
                for (var i = 0; i < ev.dataTransfer.files.length; i++) {
                    console.log('... file[' + i + '].name = ' + ev.dataTransfer.files[i].name);
                }
            }
        }

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
    });
})();