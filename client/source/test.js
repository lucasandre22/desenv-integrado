import PDFDocument from 'pdfkit-table';
import * as fs from 'fs';

// init document
let doc = new PDFDocument({ margin: 30, size: 'A4' });
// save document
doc.pipe(fs.createWriteStream("./document.pdf"));

;(async function(){
    // renderer function inside json file
    const tableJson = { 
      "headers": [
        { "label":"Nome imagem", "property":"image", "width": 250 },
        { "label":"Tempo inicio", "property":"startTime", "width": 50 },
        { "label":"Tempo fim", "property":"endTime", "width": 50 }
      ],
      "datas": reportOutputs,
      "options": {
        "width": 300
      }
    };
    // the magic
    doc.table(tableJson);
    // done!
    doc.end();
})();

/*"datas": [
        {
            "image": 'N_CGNR_11-12-2022_20:18:09_11-12-2022_20:18:09_900_2.bmp',
            "algorithm": 'CGNR',
            "imageLength": 30,
            "pixelsLength": 900,
            "date": '11-12-2022',
            "startTime": '20:18:09',
            "endTime": '20:18:09',
            "totalIterations": 2,
            "timeToComplete": 196
        }
      ],*/