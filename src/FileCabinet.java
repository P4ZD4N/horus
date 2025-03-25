import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {

    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return flattenFolders()
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return flattenFolders()
                .filter(folder -> folder.getSize().equals(size))
                .toList();
    }

    @Override
    public int count() {
        return (int) flattenFolders().count();
    }

    private Stream<Folder> flattenFolders() {
        return folders.stream()
                .flatMap(folder -> folder instanceof MultiFolder multiFolder
                        ? Stream.concat(Stream.of(folder), multiFolder.getFolders().stream())
                        : Stream.of(folder));
    }
}
