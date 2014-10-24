echo on

rem  Please see For instructions on how to configure your system, please see "ReadMe-FLA.txt"
rem Compute the placement of the links in the linkage-assembly

set WORKING_DIR=%~sdp0
set ERROR_CODE=0
set ERROR_MSG=""

rem *****************************************
rem * Update Assembly components with Versors
rem *****************************************

set EXE_JAR_NAME=freed_linkage_assembler.jar
set EXE_JAR_PATH=%WORKING_DIR%%EXE_JAR_NAME%
set CAS_ORIG=%WORKING_DIR%CADAssembly.xml
set CAS_AUG=%WORKING_DIR%CADAssembly_aug.xml
set CAS_SCAD=%WORKING_DIR%CADAssembly.scad

set JAVA_PATH=%windir%\SysWOW64\java.exe
if exist %JAVA_PATH% goto :JAVA_FOUND
set JAVA_PATH=%windir%\System32\java.exe
if exist %JAVA_PATH% goto :JAVA_FOUND
set JAVA_PATH=%windir%\Sysnative\java.exe
if exist %JAVA_PATH% goto :JAVA_FOUND

@echo off
echo   Error: could not find %JAVA_PATH%.
echo      Your system is not configured to run %JAVA_PATH%.
echo     See ReadMe-FLA.txt for instructions.
goto  :ERROR_SECTION

:JAVA_FOUND
rem %JAVA_PATH%
%JAVA_PATH% -Xmx1024m -jar "%EXE_JAR_PATH%" -i "%CAS_ORIG%" -o "%CAS_AUG%" -s "%CAS_SCAD%"

set ERROR_CODE=%ERRORLEVEL%
if %ERROR_CODE% NEQ 0 (
set ERROR_MSG="Error from %EXE_JAR_NAME% encountered during execution, error level is %ERROR_CODE%."
goto :ERROR_SECTION
)

rem ******************************************
rem * Copy STL
rem ******************************************
set COPY_STL=%WORKING_DIR%Copy_STL.cmd

if exist %COPY_STL% goto :STL_PRESENT
@echo off
echo   Error: could not find %COPYCOPY_STL%.
echo      Your system is not configured to run %COPY_STL%.
eccho     See ReadMe-FLA.txt for instructions.
goto  :ERROR_SECTION

:STL_PRESENT
cmd /c %COPY_STL%


rem ******************************************
rem * Show the assembly in OpenSCAD visualizer
rem ******************************************
set START_SCAD=%PROGRAMFILES(X86)%\OpenSCAD\openscad.exe
cmd /c %START_SCAD%

exit 0

:ERROR_SECTION
set ERROR_CODE=%ERRORLEVEL%
set ERROR_MSG="Encountered error during execution, error level is %ERROR_CODE%"
echo %ERROR_MSG% >>_FAILED.txt
echo ""
echo "See Error Log: _FAILED.txt"
exit /b %ERROR_CODE%
