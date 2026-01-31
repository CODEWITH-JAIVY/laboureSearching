import { useEffect, useState } from "react";

export default function ProfileDetails({ profile, onSave }) {
  const [form, setForm] = useState(profile);
  const [edit, setEdit] = useState(false);
  const [saving, setSaving] = useState(false);

  // 🔥 sync when profile updates
  useEffect(() => {
    setForm(profile);
  }, [profile]);

  const handleChange = (key, value) => {
    setForm({ ...form, [key]: value });
  };

  const handleSave = async () => {
    try {
      setSaving(true);
      await onSave(form);
      setEdit(false);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="bg-white rounded-2xl shadow p-6">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-6">
        <h3 className="text-xl font-bold">Profile Details</h3>

        {!edit ? (
          <button
            onClick={() => setEdit(true)}
            className="text-blue-600 font-medium"
          >
            Edit
          </button>
        ) : (
          <button
            onClick={() => {
              setEdit(false);
              setForm(profile);
            }}
            className="text-gray-500 font-medium"
          >
            Cancel
          </button>
        )}
      </div>

      {/* FORM */}
      {[
        { key: "name", label: "Name" },
        { key: "mobile", label: "Mobile" },
        { key: "city", label: "City" },
      ].map(({ key, label }) => (
        <div key={key} className="mb-4">
          <label className="block text-sm font-medium mb-1">{label}</label>

          <input
            value={form[key] || ""}
            disabled={!edit}
            onChange={(e) => handleChange(key, e.target.value)}
            className={`w-full px-4 py-2 rounded-lg border ${
              edit ? "bg-white" : "bg-gray-100 cursor-not-allowed"
            }`}
          />
        </div>
      ))}

      {/* SAVE */}
      {edit && (
        <button
          onClick={handleSave}
          disabled={saving}
          className="mt-4 bg-green-600 text-white px-6 py-2 rounded-lg"
        >
          {saving ? "Saving..." : "Save Changes"}
        </button>
      )}
    </div>
  );
}
