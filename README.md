# Countdown progressbar
[![](https://jitpack.io/v/DongSu-Park/CountDownProgress.svg)](https://jitpack.io/#DongSu-Park/CountDownProgress)


## 1. What is CountDown Progressbar
Android 라이브러리의 ProgressBar와, CountDown을 실행하는 CountDownTimer 스레드를 이용해  
CountDown Progressbar 커스텀 뷰를 제작하였습니다.

## 2. Demo

<img width="30%" src="https://user-images.githubusercontent.com/41635289/166193550-6196f397-83ea-4771-8647-a5d98a0cc091.gif"/>


## 3. Setup

- Complie sdk version : 30
- MinSdk version : 19 (4.4 kitkat)
- Recommand sdk version : 23 (6.0 Mashmellow) 또는 그 이상

```java
repositories {
	google()
	mavenCentrial()
	maven {
		url "https://jitpack.io"
	}
}

dependencies {
	....
	implementation 'com.github.DongSu-Park:CountDownProgress:0.0.7-alpha'
}
```

## 4. How to use

1. xml
    
    ```xml
    // example.xml
    <kr.co.zetta.countdownprogresslib.CountDownProgress
            android:id="@+id/layout_custom_countdown"
            android:layout_width="300dp"
            android:layout_height="50dp"
            app:progressColorTint="#77da8a"
            app:progressColorTintBg="@color/background_progress_color"
            app:progressRadius="8"
            app:progressText="test message"
            app:progressTextSize="16"
            app:progressTextColor="#000000"/>
    ```
    
    - app:progressDrawableCustom
        
        라이브러리 내의 default drawable을 사용하지 않고 커스텀된 별도의 drawable을 사용할 경우  
        해당 레퍼런스 타입을 선언하고 지정해줘야 합니다.  
        
        ```xml
        <kr.co.zetta.countdownprogresslib.CountDownProgress
                android:id="@+id/layout_custom_countdown"
                android:layout_width="300dp"
                android:layout_height="50dp"
                app:progressDrawableCustom="@drawable/YOUR_CUSTOM_DRAWABLE"
                .../>
        ```
        
    - app:progressColorTintBg (Default drawable only)
        
        프로그래스바의 배경 색상을 설정합니다. (커스텀 drawable을 사용하지 않을 경우에만 작동됩니다.)
        
    - app:progressColorTint (Default drawable only)
        
        프로그래스바의 진행 색상을 설정합니다. (커스텀 drawable을 사용하지 않을 경우에만 작동됩니다.)
        
    - app:progressRadius (Default drawable only)
        
        프로그래스바의 radius 값을 설정합니다. (커스텀 drawable을 사용하지 않을 경우에만 작동됩니다.)
        
        ```xml
        <kr.co.zetta.countdownprogresslib.CountDownProgress
                android:id="@+id/layout_custom_countdown"
                android:layout_width="300dp"
                android:layout_height="50dp"
                ...
                app:progressColorTint="#77da8a"
                app:progressColorTintBg="@color/background_progress_color"
                app:progressRadius="8"
                .../>
        ```
        
    - app:progressText
        
        프로그래스바 내의 텍스트를 설정합니다.
        
    - app:progressText
        
        프로그래스바 내의 텍스트의 크기를 설정합니다.
        
    - app:progressTextColor
        
        프로그래스바 내의 텍스트의 색상을 설정합니다.
        
        ```xml
        <kr.co.zetta.countdownprogresslib.CountDownProgress
                android:id="@+id/layout_custom_countdown"
                android:layout_width="300dp"
                android:layout_height="50dp"
                ...
                app:progressText="test message"
                app:progressTextSize="16"
                app:progressTextColor="#000000"/>
        ```
        
2. Code
    - init
        
        ```java
        public class MainActivity extends AppCompatActivity {
          private CountDownProgress mCountDownProgress;
          
          @Override
          protected void onCreate(Bundle savedInstanceState){
            ...
            initView();
            ...
          }
          
          private void initView(){
            mCountDownProgress = (CountDownProgress) findViewById(R.id.layout_custom_countdown);
            ...
          }
        }
        ```
        
    
    - setProgressText(String message)
        
        프로그래스바의 텍스트를 코드 내에서 설정합니다.
        
    - setProgressTextSize(int textSize)
        
        프로그래스바의 텍스트 크기를 코드 내에서 설정합니다.
        
    - setProgressTextColor (String colorValue)
        
        프로그래스바의 텍스트 색상을 코드 내에서 설정합니다.
        
        <aside>
        💡 String 내에 컬러값은 #RRGGBB, #AARRGGBB 타입으로 지정해야 합니다. 그렇지 않는 경우 illegalArgumentException이 발생합니다.
        </aside>
        
        ```java
        // MainActivity.java
        ...
        
        private void setupView() {
          mCountDownProgress.setProgressText("your message");
          mCountDownProgress.setProgressTextSize(16);
          mCountDownProgress.setProgressTextColor("#000000");
        }
        ```
        
    - setProgressColorTintBg (String colorValue) (Default drawable only)
        
        프로그래스바의 백그라운드 색상을 코드 내에서 설정합니다. (커스텀 drawable을 사용하지 않을 경우에만 작동됩니다.)
        
    - setProgressColorTint (String colorValue) (Default drawable only)
        
        프로그래스바의 진행 색상을 코드 내에서 설정합니다. (커스텀 drawable을 사용하지 않을 경우에만 작동됩니다.)
        
    - setProgressRadius (float radius) (Default drawable only)
        
        프로그래스바의 radius 값을 코드 내에서 설정합니다. (커스텀 drawable을 사용하지 않을 경우에만 작동됩니다.)
        
        <aside>
        💡 String 내에 컬러값은 #RRGGBB, #AARRGGBB 타입으로 지정해야 합니다. 그렇지 않는 경우 illegalArgumentException 이 발생합니다.
        
        </aside>
        
        ```java
        // MainActivity.java
        ...
        
        public void setupView() {
        	mCountDownProgress.setProgressColorTintBg("#FFFFFF");
        	mCountDownProgress.setProgressColorTint("#000000");
        	mCountDownProgress.setProgressRadius(8.0f);
        }
        ```
        
3. CountDown 실행
    - listener
        
        ```java
        // MainActivity.java
        ...
        
        private void initListener(){
          ...
          mCountDownProgress.setOnCountDownFinishEvent(new CountDownProgress.CountDownFinishListener() {
            @Override
            public void onFinished() {
               // 카운트다운이 완전히 끝난 이후 이벤트가 발생됩니다. 이후에 작동할 로직을 넣어주세요.
            }
            
            @Override
            public void onPaused(long runningTime, long remainTime){
              // 카운트다운이 중간에 멈춘 경우 이벤트가 발생됩니다. 필요시 반환되는 값을 활용해주세요.
            }
          });
        }
        ```
        
        - onFinished()
            
            사용자가 지정한 카운트다운 시간이 종료된 이후 발생하는 리스너입니다.  
            해당 메서드 내에 카운트 다운이 끝난 이후 작동할 로직을 넣어주세요
            
        - onPaused(long runningTime, long remainTime)
            
            사용자가 지정한 카운트다운 진행 중에 멈춘경우 (onPause() 메서드를 선언한 경우 작동) 발생하는 리스너 입니다.  
            해당 메서드 내에서 카운트 다운이 멈춘 이후 작동할 로직을 넣어주세요
            
            - long runningTime - 카운트다운이 멈추고 지나간 시간을 반환합니다.
            - long remainTime - 카운트다운이 멈추고 남은 시간을 반환합니다.
    
    - 카운트다운 상태 확인
    	- getCurrentState()

            카운트다운 프로그래스바의 상태를 확인합니다. State 상태값은 다음과 같습니다.  
	    
            - CountDownProgress.STATE_IDLE - 프로그래스바가 정지되어 있는 상태입니다. (init, onCancel())
            - CountDownProgress.STATE_START - 프로그래스바가 진행되고 있는 상태입니다. (onStart())
            - CountDownProgress.STATE_PAUSE - 프로그래스바가 일시정지된 상태입니다. (onPause())
            - CountDownProgress.STATE_RESTART - 프로그래스바가 일시정지 이후 재시작된 상태 입니다. (onRestart())
    
    - 카운트다운 실행 메서드
        - onStart()
            
            프로그래스바의 카운트다운을 시작합니다. 기본적으로 10초를 할당합니다.
            
        - onStart(long time)
            
            사용자가 지정한 시간으로 프로그래스바의 카운트다운을 시작합니다.
            
        - onStart(long runningTime, long remainTime)
            
            프로그래스바의 진행시간과 남은시간을 지정하여 카운트다운을 시작합니다.  
            예를 들어 10초의 전체 시간 중 8초는 이미 지나갔으며 나머지 2초를 카운트다운을 처리하려면 다음과 같습니다.
            
            ```java
            mCountDownProgress.onStart(8000, 2000);
            ```
            
    - 카운트다운 일시정지 메서드
        - onPause()
            
            프로그래스바의 카운트다운을 일시정지 합니다.  
            일시정지 이후 리스너에서 현재 진행시간, 남은시간 값을 반환받습니다.  
	    프로그래스바가 이전에 시작 또는 재시작을 호출한 경우에 작동합니다. (onStart, onRestart)  
            
    - 카운트다운 재시작
        - onRestart()
            
            프로그래스바의 카운트다운을 일시정지한 시점에서 재시작 합니다. 
            프로그래스바가 이전에 일시정지를 호출한 경우만 작동합니다. (onPause)
            
        - onRestart(long remainTime)
            
            프로그래스바의 카운트다운을 사용자가 지정한 남은 시간에 맞게 재시작합니다. 
            프로그래스바가 이전에 일시정지를 호출한 경우에 작동합니다. (onPause)
            
    - 카운트다운 취소, 스킵
        - onCancel()
            
            프로그래스바의 카운트다운을 진행 중에 취소합니다. 이후 시작 값은 초기화가 됩니다.  
            프로그래스바가 이전에 시작 또는 재시작을 호출한 경우에 작동합니다. (onstart, onRestart)
            
        - onSkip()
            
            프로그래스바 진행 중 남은 시간을 무시하고 카운트다운을 종료시킵니다. 이후 리스너에서 onFinished()의 이벤트를 받습니다.  
            프로그래스바가 이전에 시작, 재시작, 일시정지를 호출한 경우에 작동합니다. (onstart, onRestart, onPause)
            
    - 카운트다운 release
        - onRelease()
            
            프로그래스바를 액티비티에서 완전히 자원 해제할 경우에 사용합니다.
