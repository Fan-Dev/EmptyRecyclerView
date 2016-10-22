# EmptyRecyclerView
基于IRecylerView拓展支持空列表和简化api

1.使用时需要先添加IRecylerView的引用

`
compile 'com.android.support:recyclerview-v7:23.4.0'
compile 'com.github.Aspsine:IRecyclerView:0.0.3@aar'
`

2.但是他暂时不在JCenter库中，所以还需要现在项目的build.gradle中添加资源库配置如下：

`
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
`
