import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import {
  getProfileService,
  uploadProfileImageService,
  updateProfileService,
} from "../services/profile.service";

import ProfileHeader from "../components/profile/ProfileHeader";
import ProfileTabs from "../components/profile/ProfileTabs";
import EditProfileModal from "../components/profile/EditProfileModal";
import ImageCropper from "../components/profile/ImageCropper";
import imageCompression from "browser-image-compression";

export default function Profile() {
  const { login } = useContext(AuthContext);

  const [profile, setProfile] = useState(null);
  const [showEdit, setShowEdit] = useState(false);

  // image upload
  const [cropImage, setCropImage] = useState(null);
  const [uploading, setUploading] = useState(false);
  const [uploadPercent, setUploadPercent] = useState(0);

  useEffect(() => {
    getProfileService().then((res) => {
      setProfile(res);
      login({ user: res });
    });
  }, []);

  if (!profile) return <p className="p-6">Loading profile...</p>;

  return (
    <div className="max-w-6xl mx-auto px-6 py-10 space-y-8 bg-[#F4F6F8] min-h-screen">
      {/* ===== HEADER ===== */}
      <ProfileHeader
        user={profile}
        uploading={uploading}
        uploadPercent={uploadPercent}
        onEdit={() => setShowEdit(true)}
        onChangePhoto={(e) => {
          const file = e.target.files[0];
          if (!file) return;

          const reader = new FileReader();
          reader.onload = () => setCropImage(reader.result);
          reader.readAsDataURL(file);
        }}
      />

      {/* ===== TABS ===== */}
      <ProfileTabs
        profile={profile}
        onProfileUpdate={(updated) => {
          setProfile(updated);
          login({ user: updated });
        }}
      />

      {/* ===== EDIT MODAL ===== */}
      <EditProfileModal
        open={showEdit}
        profile={profile}
        onClose={() => setShowEdit(false)}
        onSave={async (data) => {
          const updated = await updateProfileService(data);
          setProfile(updated);
          login({ user: updated });
          setShowEdit(false);
        }}
      />

      {/* ===== IMAGE CROP ===== */}
      {cropImage && (
        <ImageCropper
          image={cropImage}
          onClose={() => setCropImage(null)}
          onComplete={async (file) => {
            setUploading(true);

            const compressed = await imageCompression(file, {
              maxSizeMB: 0.8,
              maxWidthOrHeight: 800,
              useWebWorker: true,
            });

            const url = await uploadProfileImageService(
              compressed,
              setUploadPercent,
            );

            const updated = { ...profile, profileImageUrl: url };
            setProfile(updated);
            login({ user: updated });

            setUploading(false);
            setCropImage(null);
          }}
        />
      )}
    </div>
  );
}
