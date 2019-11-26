/**
 * The jQuery UI plugin namespace.
 * Dependency : jQuery
 */

/**
 * jqueryui datepicker 기본 설정
 */
$.datepicker.regional['ko'] = {
	showOn : "both",
	buttonText : '',
	closeText : "닫기",
	prevText : "이전달",
	nextText : "다음달",
	currentText : "오늘",
	monthNames : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	monthNamesShort : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
	dayNames : [ "일", "월", "화", "수", "목", "금", "토" ],
	dayNamesShort : [ "일", "월", "화", "수", "목", "금", "토" ],
	dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
	weekHeader : "Wk",
	dateFormat : "yy-mm-dd",
	firstDay : 0,
	isRTL : false,
	showMonthAfterYear : true,
	yearSuffix : "<span>년</span>",
	beforeShow : function(i) {
		// if ($(i).attr('disabled')) {
		// return false;
		// }
	}
};
$.datepicker.setDefaults($.datepicker.regional['ko']);

/**
 * jquery timepicker 기본 설정
 */
$.timepicker.regional['ko'] = {
	showOn : "focus",
	timeOnlyTitle : '시간 선택',
	timeText : '시간',
	hourText : '시',
	minuteText : '분',
	secondText : '초',
	millisecText : '밀리초',
	microsecText : '마이크로',
	timezoneText : '표준 시간대',
	currentText : '현재 시각',
	closeText : '닫기',
	timeFormat : 'HH:mm',
	timeSuffix : '',
	amNames : [ '오전', 'AM', 'A' ],
	pmNames : [ '오후', 'PM', 'P' ],
	isRTL : false,
	showSecond : false,
	showMillisec : false,
	showMicrosec : false,
	showTimezone : false
};
$.timepicker.setDefaults($.timepicker.regional['ko']);

/**
 * 프로젝트의 모든 날짜 포멧을 담당한다.
 * 
 * @param {date}
 *            cellvalue date.getTime()의 값
 * @returns String
 * @example dateFormatter(2912031230) -> 2014-03-12
 */
dateFormatter = function(cellvalue) {
	if (cellvalue) {
		var gap = new Date().getTimezoneOffset() * (-60000);
		var date = new Date(cellvalue + gap);
		// return $.date(date);
		// return $.datepicker.formatDate('yy-mm-dd', date);
		// return date.toString();
		// return date.toLocaleString();
		// return date.toUTCString();

		function f(n) {
			return n < 10 ? '0' + n : n;
		}

		return date.getUTCFullYear() + '-' + f(date.getUTCMonth() + 1) + '-' + f(date.getUTCDate()) + ' '
				+ f(date.getUTCHours()) + ':' + f(date.getUTCMinutes());// +
		// ':';
		// +f(this.getUTCSeconds()) + 'Z';

		// return date.toJSON();
	}
	return '-';
}

/**
 * 날짜 범위 지정을 위한 Datepicker용 함수
 * 
 * @example datepickerRange("from", "to", cb)
 */
datepickerRange = function(startId, endId, onCloseCb) {
	$("#" + startId).datepicker({
		defaultDate : "+1w",
		numberOfMonths : 1,
		maxDate : new Date(),
		onClose : function(selectedDate) {
			if (selectedDate) {
				$("#" + endId).datepicker("option", "minDate", selectedDate);
				$('button.ui-datepicker-trigger').button({
					text : false,
					icons : {
						primary : 'ui-calendar-button',
						secondary : ''
					}
				});
				if (onCloseCb) {
					onCloseCb();
				}
			}
		}
	});
	$("#" + endId).datepicker({
		defaultDate : "+1w",
		numberOfMonths : 1,
		maxDate : new Date(),
		onClose : function(selectedDate) {
			if (selectedDate) {
				$("#" + startId).datepicker("option", "maxDate", selectedDate);
				$('button.ui-datepicker-trigger').button({
					text : false,
					icons : {
						primary : 'ui-calendar-button',
						secondary : ''
					}
				});
				if (onCloseCb) {
					onCloseCb();
				}
			}
		}
	});
}