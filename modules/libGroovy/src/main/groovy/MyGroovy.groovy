class MyGroovy {
    static void main(String[] args) {
        def android = {
            compileSdkVersion 27
            defaultConfig {
                versionName "1.0"
            }
        }
        Android a = new Android()
        //将闭包与具体对象关联起来
        android.dehydrate = a
        android.call()
        println ("$a")
    }
}

class DefaultConfig {
    private String versionName

    def versionName(String versionName) {
        this.versionName = versionName
    }

    @Override
    String toString() {
        return "DefaultConfig{ versionName = $versionName}"
    }
}

class Android {
    private int compileSdkVersion
    private DefaultConfig defaultConfig

    Android() {
        this.defaultConfig = new DefaultConfig()
    }

    def compileSdkVersion(int compileSdkVersion) {
        this.compileSdkVersion = compileSdkVersion
    }

    def defaultConfig(Closure closure) {
        closure.setDelegate(defaultConfig)
        closure.call()
    }

    @Override
    String toString() {
        return "Android{ compileSdkVersion = $compileSdkVersion,defaultConfig = $defaultConfig }"
    }
}