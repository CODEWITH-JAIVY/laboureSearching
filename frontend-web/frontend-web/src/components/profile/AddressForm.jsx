import { useState } from "react";
import { saveAddressService } from "../../services/profile.service";

export default function AddressForm({ address, onSaved }) {
  const [form, setForm] = useState(address || {});
  const [saving, setSaving] = useState(false);

  const handle = (k, v) =>
    setForm({ ...form, [k]: v });

  const save = async () => {
    try {
      setSaving(true);
      const saved = await saveAddressService(form);
      onSaved?.(saved);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="bg-[#F6F8FA] p-6 rounded-xl space-y-4">
      <input placeholder="City" onChange={e => handle("city", e.target.value)} className="input"/>
      <input placeholder="Locality" onChange={e => handle("locality", e.target.value)} className="input"/>
      <input placeholder="Landmark" onChange={e => handle("landmark", e.target.value)} className="input"/>
      <input placeholder="Pincode" onChange={e => handle("pincode", e.target.value)} className="input"/>

      <button
        onClick={save}
        className="bg-green-600 text-white px-4 py-2 rounded"
      >
        {saving ? "Saving..." : "Save Address"}
      </button>
    </div>
  );
}
