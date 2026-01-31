import { View, Text, TextInput } from "react-native";
import { useContext } from "react";
import { LanguageContext } from "../context/LanguageContext";

export default function SearchBar() {
  const { t } = useContext(LanguageContext);

  return (
    <View className="px-4 mt-4">
      <View className="bg-white rounded-2xl shadow p-4 space-y-3">
        
        {/* Search Input */}
        <View className="flex-row items-center gap-2 border rounded-xl px-3 py-2">
          <Text>🔍</Text>
          <TextInput
            placeholder={t("searchPlaceholder")}
            className="flex-1 text-sm"
          />
        </View>

        {/* Location */}
        <View className="flex-row items-center gap-2">
          <Text className="text-gray-600">📍</Text>
          <Text className="text-gray-600 text-sm">
            {t("location")}
          </Text>
        </View>

      </View>
    </View>
  );
}
