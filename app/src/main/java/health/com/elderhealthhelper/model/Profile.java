package health.com.elderhealthhelper.model;

import android.graphics.drawable.Drawable;

/**
 * Created by mpifa on 22/11/15.
 */
public class Profile {
    private String name;
    private String email;
    private String location;
    private String imagePath;

    public Profile(String name, String email, String location, String imagePath) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Drawable getImageAsDrawable(){
        return Drawable.createFromPath(imagePath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (name != null ? !name.equals(profile.name) : profile.name != null) return false;
        if (email != null ? !email.equals(profile.email) : profile.email != null) return false;
        if (location != null ? !location.equals(profile.location) : profile.location != null)
            return false;
        return !(imagePath != null ? !imagePath.equals(profile.imagePath) : profile.imagePath != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        return result;
    }
}
