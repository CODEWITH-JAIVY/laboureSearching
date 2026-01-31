import { useState } from "react";
import ProfileDetails from "./ProfileDetails";
import AddressForm from "./AddressForm";
import LiveLocation from "./LiveLocation";

export default function ProfileTabs({ profile, onProfileUpdate }) {
  const [tab, setTab] = useState("details");

  return (
    <div className="bg-[#FDFEFE] rounded-xl p-6">
      <div className="flex gap-6 border-b mb-6">
        {["details", "address", "location"].map((t) => (
          <button
            key={t}
            onClick={() => setTab(t)}
            className={`pb-2 ${
              tab === t ? "border-b-2 border-blue-600 text-blue-600" : ""
            }`}
          >
            {t === "details" && "Details"}
            {t === "address" && "Address"}
            {t === "location" && "Live Location"}
          </button>
        ))}
      </div>

      {tab === "details" && (
        <ProfileDetails profile={profile} onSave={onProfileUpdate} />
      )}
      {tab === "address" && (
        <AddressForm address={profile.address} onSaved={onProfileUpdate} />
      )}
      {tab === "location" && (
        <LiveLocation onSaved={onProfileUpdate} />
      )}
    </div>
  );
}
