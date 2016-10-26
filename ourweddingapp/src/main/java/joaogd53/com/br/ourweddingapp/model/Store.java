package joaogd53.com.br.ourweddingapp.model;

public class Store {
    private int idStore;
    private String name;
    private int imgSmall;
    private int imgLarge;
    private String phone;
    private String url;

    private Store() {
        this.name = "Loja " + this.idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgLarge() {
        return imgLarge;
    }

    public void setImgLarge(int imgLarge) {
        this.imgLarge = imgLarge;
    }

    public int getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(int imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class StoreBuilder {
        public static Store buildStore() {
            Store store = new Store();
            return store;
        }

//        public static Store buildStore(int id) {
//            try {
//                return Store.findStoreById(id);
//            } catch (Exception ex) {
//                Store store = new Store();
//                store.idStore = id;
//                store.name = "Loja " + id;
//                storeList.put(id, store);
//                return store;
//            }
//        }
    }
}
