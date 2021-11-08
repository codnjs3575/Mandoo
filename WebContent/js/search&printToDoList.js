/**
 * 
 */
function handleOnChange(e) {
  // 선택된 데이터의 텍스트값 가져오기
  const text = e.options[e.selectedIndex].text;
  console.log(e.options);

	if(text=="우선순위별")
		{funcPrintLanking();}
	else{}
}


//todayArr[l]-7*parseInt(todayArr[l]/7)은 요일넘버
//(todayArr[l]-(todayArr[l]-7*parseInt(todayArr[l]/7)))/7+1은 칸번호

var funcPrintLanking = function(){
	//우선순위 1
	for(var l=0;l<count;l++)
	{
		if(lank[(todayArr[l]-(todayArr[l]-7*parseInt(todayArr[l]/7)))/7+1]==1)
		{
			var printListContents = contents[(todayArr[l]-(todayArr[l]-7*parseInt(todayArr[l]/7)))/7+1];
			checkboxplus = document.createElement("input");
			checkboxplus.type="checkbox";
			checkboxplus.name="finished";
			checkboxplus.value=printListContents;
			document.getElementById("ToDoList").appendChild(checkboxplus);
			document.write('&nbsp'+'<span id="lank1">'+printListContents+'</span>'+'&nbsp'+'&nbsp');
			document.write('<br>'+'&nbsp'+'&nbsp'+'&nbsp'+'&nbsp'+'&nbsp');
			document.write('<br>');
		}
	}
}
            