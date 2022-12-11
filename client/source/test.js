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
        //{ "label":"Nome imagem", "property":"jsonProperty", "width":100 },
        { "label":"Nome imagem", "property":"name", "width":100 },
        { "label":"Tempo", "property":"age", "width":100 },
        { "label":"Year", "property":"year", "width":100 }
      ],
      "datas": [
        { "name":"bold:Name 1", "age":"Age 1", "year":"Year 1" },
        { "name":"Name 2", "age":"Age 2", "year":"Year 2" },
        { "name":"Name 3", "age":"Age 3", "year":"Year 3",
          "renderer": "function(value, i, irow){ return value + `(${(1+irow)})`; }"
        }
      ],
      "rows": [
        [ "Name 4", "Age 4", "Year 4" ]
      ],
      "options": {
        "width": 300
      }
    };
    // the magic
    doc.table(tableJson);
    // done!
    doc.end();
  })();