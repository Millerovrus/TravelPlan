

function inputData() {
    var dataFrom = document.getElementById('inputFrom').value; // откуда
    var dataTo = document.getElementById('inputTo').value;  // куда
    var dataDate = document.getElementById('inputDate').value; // дата
    var dataType = document.getElementById('inputFrom').value; // тип

    var div = document.createElement("div"); // Создаем элемент div
    div.innerHTML = "<p>"+dataFrom+" "+dataTo+" "+dataDate+" "+dataType+"</p>"; // Наполняем созданный div содержанием с подстановкой значений
    document.getElementById("outputData").appendChild(div); // Все вкладывается в <div id="outputData"></div>
}

