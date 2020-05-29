

### 依赖

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