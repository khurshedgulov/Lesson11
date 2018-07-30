package company.my.lesson11wallpaperchanger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GalleryFragment extends Fragment {
    // Fragment в отличии от Activity инициализируется через функцию
    // onCreateView, которая возвращает View данного фрагмента
    // возвращаемое View это главный контейнер (ImageView в файле макета в данном случае)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        // Привязка макета к фрагменту
        View v = inflater.inflate(R.layout.screen_slide, parent, false);
        ImageView imageView = v.findViewById(R.id.content);
        // Установка изображения виджету ImageView, ID которого получени через аргументы с ключом image
        imageView.setImageDrawable(getResources().getDrawable(getArguments().getInt("image")));
        // Возвращение корневого (первого) элемента для показа
        return v;
    }
}
