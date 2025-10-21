@ECHO OFF
SETLOCAL

SET APP_BASE_NAME=%~n0
SET APP_HOME=%~dp0

SET DEFAULT_JVM_OPTS=-Xmx64m -Xms64m

IF NOT "%JAVA_HOME%"=="" GOTO findJavaFromJavaHome

SET JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
IF "%ERRORLEVEL%" == "0" GOTO init

ECHO.
ECHO ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
GOTO fail

:findJavaFromJavaHome
SET JAVA_HOME=%JAVA_HOME:"=%
SET JAVA_EXE=%JAVA_HOME%\bin\java.exe

IF EXIST "%JAVA_EXE%" GOTO init

ECHO.
ECHO ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
ECHO Please set the JAVA_HOME variable in your environment to match the
ECHO location of your Java installation.

:fail
PAUSE
EXIT /B 1

:init
SET WRAPPER_JAR=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
SET WRAPPER_B64=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar.base64

IF NOT EXIST "%WRAPPER_JAR%" (
    IF EXIST "%WRAPPER_B64%" (
        powershell -NoProfile -ExecutionPolicy Bypass -Command "\$b64Path = [System.IO.Path]::GetFullPath('%WRAPPER_B64%'); \$jarPath = [System.IO.Path]::GetFullPath('%WRAPPER_JAR%'); \$bytes = [System.Convert]::FromBase64String((Get-Content -Raw -Path \$b64Path)); [System.IO.File]::WriteAllBytes(\$jarPath, \$bytes)" >NUL
        IF NOT EXIST "%WRAPPER_JAR%" (
            ECHO.
            ECHO ERROR: Failed to decode Gradle wrapper JAR.
            GOTO fail
        )
    ) ELSE (
        ECHO.
        ECHO ERROR: Gradle wrapper JAR is missing. Run "gradle wrapper" to regenerate it.
        GOTO fail
    )
)

SET CLASSPATH=%WRAPPER_JAR%

SET CMD_LINE_ARGS=
:parse
IF "%1"=="" GOTO execute
SET CMD_LINE_ARGS=%CMD_LINE_ARGS% "%1"
SHIFT
GOTO parse

:execute
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%

ENDLOCAL
EXIT /B 0
