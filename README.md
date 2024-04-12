> Step 1. Add the JitPack repository to your build file

```gradle
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

 > Step 2. Add the dependency

```gradle
dependencies {
	        implementation 'com.github.debasish7377:TOTImageView:1.0'
	}
```
>TOTImageView implementation

``` Toast implementation

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)

        //Place your image url
        val imageUrl = "https://www.youtube.com/@thugsoftechnology"
        //Place your image url

        TOTImageView(imageView)
            .placeholder(R.drawable.avatar)
            .src(imageUrl)
            .load(this)

        
        
        //if you want circle image then place circle
        TOTImageView(imageView)
            .circle()
            .placeholder(R.drawable.avatar)
            .src(imageUrl)
            .load(this)
        //if you want circle image then place circle

    }
}

```
