# 实验2-界面布局
## 1.线性布局
&emsp;&emsp;在1个垂直线性布局中嵌套4个水平线性布局，实现类似表格的效果
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="176dp"
        android:orientation="vertical">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="horizontal">
        </LinearLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="horizontal">
        </LinearLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="horizontal">
        </LinearLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="horizontal">
        </LinearLayout>
    </LinearLayout>
</LinearLayout>


```
&emsp;&emsp;在每个水平布局中放置四个按钮构件，调整各按钮的宽度以达到示例中的效果
```java
        <Button
        android:id="@+id/button"
        android:layout_width="80dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:text="One,One" />

        <Button
        android:id="@+id/button2"
        android:layout_width="140dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:text="One,Two" />

        <Button
        android:id="@+id/button4"
        android:layout_width="80dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:text="One,Three" />

        <Button
        android:id="@+id/button5"
        android:layout_width="80dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:text="One,Four" />
```
&emsp;&emsp;最终效果如图：
![线性布局](/2_layoutTest/screenshot/linearlayout.jpg)

## 2.表格布局
&emsp;&emsp;在表格布局中添加7个tablerow容器,设置背景色为黑色
```java
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/black"
    android:baselineAligned="true">

    <TableRow
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </TableRow>
    ...
</TableLayout>
```
&emsp;&emsp;在第一行中添加一个textview，修改其背景色为灰色，字体颜色白色，为其设置android:layout_span属性，使其位置横跨两列
```java
<TextView
            android:id="@+id/textView3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_span="2"
            android:text="Hello TableLayout"
            android:textColor="@color/white" />
```
&emsp;&emsp;在其余6行中各添加3个textview，在TableLayout的标签中设置以下两个属性，shrinkColumns用于指示可根据内容缩窄的列的序号，stretchColumns用于指示可拓宽的列的序号（列序号从0开始）
```java
    android:shrinkColumns="0"
    android:stretchColumns="1,2"
```
&emsp;&emsp;给位于第三列的textview设置右对齐属性
```java
    android:gravity="right"
```
&emsp;&emsp;修改字体颜色为灰色，在第4、5行和第6、7行间添加一条灰色的水平线
```java
<View
        android:layout_width="fill_parent"
        android:layout_height="1dp"
        android:background="@android:color/white"/>
```
&emsp;&emsp;最终效果如图:
![表格布局](/2_layoutTest/screenshot/tablelayout.jpg)
## 3.约束布局1
&emsp;&emsp;为每行按钮设置约束链，使其能够在水平方向上均匀分布，为每列按钮设置约束链，使其能够在竖直方向上均匀分布，具体约束关系见下图
![约束布局1](/2_layoutTest/screenshot/constraintlayout1.jpg)

&emsp;&emsp;部分实验代码：
```java
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/constraintLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white">

    <Button
        android:id="@+id/button57"
        android:layout_width="68dp"
        android:layout_height="49dp"
        android:backgroundTint="@android:color/system_neutral1_200"
        android:text="-"
        android:textColor="@color/black"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toEndOf="@+id/button56"
        app:layout_constraintTop_toBottomOf="@+id/button52" />
    <EditText
        android:id="@+id/editTextTextPersonName"
        android:layout_width="361dp"
        android:layout_height="58dp"
        android:background="@color/white"
        android:backgroundTint="#BDC386"
        android:ems="10"
        android:gravity="right|center_vertical"
        android:inputType="textPersonName"
        android:text="0.0"
        android:textSize="34sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.49"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.23000002" />
    <TextView
        android:id="@+id/textView22"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Input"
        android:textSize="48sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.1"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.060000002" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
&emsp;&emsp;最终效果如图:

![约束布局1](/2_layoutTest/screenshot/constraintlayout2.jpg)
## 4.约束布局2
&emsp;&emsp;本题中各构件的位置如图所示

![约束布局2](/2_layoutTest/screenshot/constraintlayout3.jpg)

&emsp;&emsp;最上面三个图标使用约束链进行水平均匀分布，再对它们设置中心线对齐的约束
![约束布局2](/2_layoutTest/screenshot/constraintlayout4.jpg)
```java
    <ImageView
        android:id="@+id/imageView4"
        android:layout_width="30dp"
        android:layout_height="30dp"
        android:layout_marginTop="15dp"
        app:layout_constraintEnd_toStartOf="@+id/imageView5"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/space_station_icon" />

    <ImageView
        android:id="@+id/imageView5"
        android:layout_width="30dp"
        android:layout_height="30dp"
        app:layout_constraintBottom_toBottomOf="@+id/imageView4"
        app:layout_constraintEnd_toStartOf="@+id/imageView6"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toEndOf="@+id/imageView4"
        app:layout_constraintTop_toTopOf="@+id/imageView4"
        app:srcCompat="@drawable/rocket_icon" />

    <ImageView
        android:id="@+id/imageView6"
        android:layout_width="30dp"
        android:layout_height="30dp"
        app:layout_constraintBottom_toBottomOf="@+id/imageView5"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toEndOf="@+id/imageView5"
        app:layout_constraintTop_toTopOf="@+id/imageView5"
        app:srcCompat="@drawable/rover_icon" />
```
&emsp;&emsp;中间的双箭头图案和左右两个textview的约束关系如图所示
![约束布局2](/2_layoutTest/screenshot/constraintlayout5.jpg)
```java
    <TextView
        android:id="@+id/textView25"
        android:layout_width="124dp"
        android:layout_height="98dp"
        android:layout_marginEnd="40dp"
        android:background="#1F6750"
        android:gravity="center"
        android:text="DCA"
        android:textColor="@color/white"
        app:layout_constraintBottom_toBottomOf="@+id/imageView7"
        app:layout_constraintEnd_toEndOf="@+id/imageView7"
        app:layout_constraintTop_toTopOf="@+id/imageView7" />

    <TextView
        android:id="@+id/textView26"
        android:layout_width="124dp"
        android:layout_height="98dp"
        android:layout_marginStart="40dp"
        android:background="#1F6750"
        android:gravity="center"
        android:text="MARS"
        android:textColor="@color/white"
        app:layout_constraintBottom_toBottomOf="@+id/imageView7"
        app:layout_constraintStart_toStartOf="@+id/imageView7"
        app:layout_constraintTop_toTopOf="@+id/imageView7" />
    <ImageView
        android:id="@+id/imageView7"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_marginBottom="40dp"
        app:layout_constraintBottom_toTopOf="@+id/guideline6"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.498"
        app:layout_constraintStart_toStartOf="parent"
        app:srcCompat="@drawable/double_arrows" />

```
&emsp;&emsp;橙色文本框、开关和中间行星图形的定位通过两条辅助线来进行
```java
    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline6"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_begin="200dp" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline7"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.05" />
```
&emsp;&emsp;最终效果如图：

![约束布局2](/2_layoutTest/screenshot/constraintlayout6.jpg)
