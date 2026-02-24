package com.example.malakfinal.data.MyTaskTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.malakfinal.R;

import java.util.List;

/**
 * MyPlantAdapter هو Adapter مخصص يُستخدم لعرض كائنات Plant
 * داخل ListView أو GridView.
 * <p>
 * هذا الـ Adapter يربط بين بيانات النباتات (Plant)
 * وبين الواجهة الرسومية التي تمثل كل عنصر في القائمة.
 */
public class MyPlantAdapter extends ArrayAdapter<Plant> {

    /** رقم مورد التصميم (layout) الخاص بكل عنصر في القائمة */
    private final int itemLayout;

    /**
     * المُنشئ الخاص بـ MyPlantAdapter.
     *
     * @param context  السياق الحالي للتطبيق (غالبًا الـ Activity)
     * @param resource رقم ملف الـ layout الذي يمثل عنصرًا واحدًا في القائمة
     * @param objects  قائمة كائنات Plant التي سيتم عرضها
     */
    public MyPlantAdapter(@NonNull Context context, int resource, @NonNull List<Plant> objects) {
         // Adapter هو وسيط يين مصدر معطيات وكائن لعرض هذه المعطيات
        super(context, resource, objects);
        this.itemLayout = resource;
    }

    /**
     * تُستخدم هذه الدالة لإنشاء أو إعادة استخدام View
     * لكل عنصر داخل ListView أو GridView، وربط بيانات النبات بها.
     *
     * @param position    موقع العنصر داخل القائمة
     * @param convertView واجهة قديمة لإعادة استخدامها (لتحسين الأداء)
     * @param parent      الواجهة الأب التي سيتم إرفاق هذا العنصر بها
     * @return View يمثل عنصرًا واحدًا في القائمة
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // إعادة استخدام الـ View إذا كان موجودًا لتحسين الأداء
        View vitem = convertView;
        if (vitem == null) {
            vitem = LayoutInflater.from(getContext()).inflate(itemLayout, parent, false);
        }

        // ربط عناصر الواجهة مع المتغيرات
        ImageView malak = vitem.findViewById(R.id.child);
        TextView tvTitle = vitem.findViewById(R.id.tvTitle);
        TextView tvText = vitem.findViewById(R.id.tvText);
        TextView tvImportance = vitem.findViewById(R.id.tvImportance);

        // جلب كائن Plant الحالي
        Plant current = getItem(position);

        // عرض بيانات النبات داخل عناصر الواجهة
        if (current != null) {
            tvTitle.setText(current.getTitle());
            tvText.setText(current.getDescription());
         //   tvImportance.setText(current.getImportance());
        }

        return vitem;
    }
}
