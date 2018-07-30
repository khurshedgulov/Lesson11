package company.my.lesson11wallpaperchanger;

import android.app.WallpaperManager;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    MyPagerAdapter adapter;

    // Список изображений для показа
    List<Integer> list = new ArrayList<Integer>(Arrays.asList(R.drawable.aston_1, R.drawable.aston_2, R.drawable.aston_3, R.drawable.aston_4, R.drawable.aston_6));

    // Кнопка установки фона
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.setBg);

        viewPager = findViewById(R.id.pager);
        // Инициализация нового экземпляра MyPagerAdapter
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        // Привязка адаптера к ViewPager
        viewPager.setAdapter(adapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение ID изображения текущей страницы
                int drawId = list.get(viewPager.getCurrentItem());
                // Получение ссылки к службе изменения обоев
                WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
                // Попытка установки нового обоя
                try {
                    wm.setResource(drawId);
                    // Вывод сообщения при замене обоя
                    Toast.makeText(getApplicationContext(), "Wallpaper changed successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e)
                {
                    // Вывод сообщения при сбое замены обоя
                    Toast.makeText(getApplicationContext(), "Error changing background", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
    // Переопределения поведения кнопки Назад
    @Override
    public void onBackPressed() {
        // Если ViewPager показывает первую страницу тогда закрыть приложение
        // (нумерация начинается с 0 как в массивах поэтому под номером 0 находится первый элемент)
        if (viewPager.getCurrentItem() == 0)
        {
            super.onBackPressed();
            // Если показываются страницы больше первой тогда
        } else {
            // перейти к предыдущей странице
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        // Список для хранения фрагментов
        List<GalleryFragment> flist = new ArrayList<>();

        // Конструктор класса, который получает в качестве аргумента FragmentManager
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        // Функция для возвращения текущего элемента
        @Override
        public GalleryFragment getItem(int position)
        {
            // Проверка запрошенного индекса и уже существующих фрагментов
            // в массиве, если запрашивается страница, которая ещё не создана
            // (когда индекс страницы больше длины массива -1), тогда инициализировать новый фрагмент
            if(position > flist.size() - 1)
            {
                // Если страница запрашивается в первый раз
                // инициализировать новый фрагмент и ссылку добавить в массив flist
                GalleryFragment gf = new GalleryFragment();
                // Инициализировать новый объект класса Bundle
                // и добавить в него значение ID изображения для этой страницы с ключом image
                Bundle b = new Bundle();
                b.putInt("image", list.get(position));
                // отправить аргументы фрагменту для обработки
                gf.setArguments(b);
                // Возвратить ссылку на фрагмент
                return gf;
            } else {
                // Если страница запрашивается в последующие разы вернуть
                // ссылку к уже сущетсвующему фрагменту
                return flist.get(position);
            }
        }

        // Служит для возвращения количества элементов в ViewPager
        @Override
        public int getCount() {
            return list.size();
        }
    }
}
