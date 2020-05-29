

### 依赖

```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

```

```java

dependencies {
    implementation 'com.github.alanplus:lib-recycleview:0.9'
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support:recyclerview-v7:28.0.0'
}


```


### 使用

- 简单用法

```java

XmRecycleView recycleView = findViewById(R.id.view);
helper = new SimpleListHelper<>(recycleView);
helper.setData(list);
helper.setOnRefreshListener(new OnRefreshListener() {
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        position = 0;
        loadData();
    }
});

helper.setOnLoadMoreListener(new OnLoadMoreListener() {
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        loadData();
    }
});

helper.setIBaseRecycleView(new IBaseRecycleView<String>() {
    @Override
    public View getView(Context context, int type) {
        TextView textView = new TextView(context);
        int i = SmartUtil.dp2px(10);
        textView.setPadding(i, i, i, i);
        textView.setBackgroundColor(Color.WHITE);
        textView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        return textView;
    }

    @Override
    public void bindView(String s, View view, int position) {
        if (view instanceof TextView) {
            ((TextView) view).setText(s);
        }
    }
});

helper.build();
helper.autoRefresh();


```