#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.
FormatTime, TimeString

sleep, 3000
;此函数通常用于不需要发送qq的情况。
Preheat("java -Dfile.encoding=utf-8 -jar QQImageGenerator.jar lootMine user1 user2 user3 user4")
;此函数的第一个参数为qq号，使用时请确保登陆qq并与其是好友关系。
AutoSend("111111111","java -Dfile.encoding=utf-8 -jar QQImageGenerator.jar tr user1")
AutoSend("111111111","java -Dfile.encoding=utf-8 -jar QQImageGenerator.jar tr user2") 
AutoSend("111111111","java -Dfile.encoding=utf-8 -jar QQImageGenerator.jar tr user3") 
AutoSend("111111111","java -Dfile.encoding=utf-8 -jar QQImageGenerator.jar tr user4") 
;自动发送消息脚本函数
AutoSend(url, message)
{
	run, cmd.exe
	sleep, 100
	clipboard := % message
	Send ^c
	ClipWait
	sleep, 200
	Send ^v
	sleep, 200
	Send {Enter}
	sleep, 4000
	send !{F4}

	run,  tencent://message/?uin=%url%
	sleep, 2000
	Send ^v
	sleep, 200
	send !s
	sleep, 400
	send !{F4}
	sleep, 200
}
Preheat(message)
{
	run, cmd.exe
	sleep, 100
	clipboard := % message
	Send ^c
	ClipWait
	sleep, 200
	Send ^v
	sleep, 200
	Send {Enter}
	sleep, 4000
	send !{F4}
}
exit