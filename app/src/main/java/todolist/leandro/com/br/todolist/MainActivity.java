package todolist.leandro.com.br.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

    private EditText textoTarefa;
    private Button botaoAdicionar;
    private ListView listaTarefas;
    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            // Recuperar componentes
            textoTarefa = findViewById(R.id.textoId);
            botaoAdicionar = findViewById(R.id.botaoAdicionarId);
            listaTarefas = findViewById(R.id.listViewId);

            // Banco dados
            bancoDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

            // tarefa tabelas
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas(id INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, tarefa VARCHAR)");
            botaoAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String textoDigitado = textoTarefa.getText().toString();
                    bancoDados.execSQL("INSERT INTO tarefas(tarefa) VALUES('" + textoDigitado + "')");

                }
            });

            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas ", null);

            // recuperar os ids das colunas
            int indiceColuna = cursor.getColumnIndex("id");
            int indiceColunaTarefa = cursor.getColumnIndex("tarefa");

            // listar as tarefas
            cursor.moveToFirst();
            while (cursor != null) {
                Log.i("Resultado - ", "Tarefa: " + cursor.getString(indiceColunaTarefa));
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
