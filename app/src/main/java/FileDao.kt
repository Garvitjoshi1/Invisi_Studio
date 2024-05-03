import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FileDao {
    @Insert
    fun insertFile(file: FileEntity)

    @Query("SELECT * FROM files")
    fun getAllFiles(): List<FileEntity>
}
