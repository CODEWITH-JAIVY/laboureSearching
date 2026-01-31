import { useEffect, useState } from "react";

export default function EditProfileModal({ open, profile, onClose, onSave }) {
  if (!open) return null;

  const [form, setForm] = useState({});

  useEffect(() => {
    setForm({
      name: profile.name || "",
      mobile: profile.mobile || "",
      city: profile.city || "",
      wage: profile.wage || "",
    });
  }, [profile, open]);

  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-[#F6F8FA] w-full max-w-md rounded-xl p-6 space-y-4">
        <h2 className="text-xl font-semibold">Edit Profile</h2>

        {["name", "mobile", "city"].map((f) => (
          <input
            key={f}
            className="input"
            value={form[f]}
            placeholder={f}
            onChange={(e) => setForm({ ...form, [f]: e.target.value })}
          />
        ))}

        {profile.userType === "LABOUR" && (
          <input
            className="input"
            value={form.wage}
            placeholder="Wage per day"
            onChange={(e) =>
              setForm({ ...form, wage: e.target.value })
            }
          />
        )}

        <div className="flex justify-end gap-3 pt-4">
          <button onClick={onClose}>Cancel</button>
          <button
            onClick={() => onSave(form)}
            className="bg-blue-600 text-white px-4 py-2 rounded"
          >
            Save
          </button>
        </div>
      </div>
    </div>
  );
}
