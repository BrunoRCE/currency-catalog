package com.github.brunorce.currencycatalog.provider

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Pruebas unitarias para [CurrencyProvider].
 * Esta clase verifica la correcta recuperación de monedas, el manejo de locales del sistema
 * y la búsqueda de monedas por código.
 */
class CurrencyProviderTest {

    /**
     * Verifica que [CurrencyProvider.getAll] devuelva la moneda local del sistema
     * como el primer elemento de la lista.
     */
    @Test
    fun `getAll should return system currency as first element`() {
        val originalLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            val currencies = CurrencyProvider.getAll()
            
            assertEquals("USD", currencies.first().code)
            assertEquals(0, currencies.first().id)
            assertTrue(currencies.size > 1)
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    /**
     * Asegura que no existan códigos de moneda duplicados en la lista devuelta por [CurrencyProvider.getAll],
     * incluso si la moneda del sistema ya está presente en el catálogo estático.
     */
    @Test
    fun `getAll should not have duplicate codes when system currency exists in catalog`() {
        val originalLocale = Locale.getDefault()
        try {
            // EUR está en CurrencyCatalog con id 1
            Locale.setDefault(Locale.GERMANY)
            val currencies = CurrencyProvider.getAll()
            
            val eurCount = currencies.count { it.code == "EUR" }
            assertEquals(1, eurCount, "Debería haber solo una entrada de EUR")
            assertEquals("EUR", currencies.first().code)
            assertEquals(0, currencies.first().id)
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    /**
     * Verifica que [CurrencyProvider.findByCode] devuelva la moneda correcta
     * cuando se proporciona un código válido existente.
     */
    @Test
    fun `findByCode should return correct currency when it exists`() {
        val currency = CurrencyProvider.findByCode("GBP")
        assertNotNull(currency)
        assertEquals("GBP", currency?.code)
        assertEquals(3, currency?.id)
    }

    /**
     * Verifica que [CurrencyProvider.findByCode] devuelva null cuando se busca
     * un código de moneda que no existe.
     */
    @Test
    fun `findByCode should return null when currency does not exist`() {
        val currency = CurrencyProvider.findByCode("INVALID")
        assertNull(currency)
    }

    /**
     * Asegura que la moneda del sistema pueda ser encontrada por su código
     * y que tenga el ID reservado 0.
     */
    @Test
    fun `findByCode should find system currency with ID 0`() {
        val originalLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            val currency = CurrencyProvider.findByCode("USD")
            assertNotNull(currency)
            assertEquals("USD", currency?.code)
            assertEquals(0, currency?.id)
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    /**
     * Valida que la búsqueda sea sensible a mayúsculas y minúsculas.
     */
    @Test
    fun `findByCode should be case sensitive (potential pitfall)`() {
        val currency = CurrencyProvider.findByCode("usd")
        assertNull(currency, "Debería devolver null para 'usd' en minúsculas porque usa coincidencia exacta")
    }

    /**
     * Verifica el comportamiento con strings vacíos.
     */
    @Test
    fun `findByCode with empty string should return null`() {
        assertNull(CurrencyProvider.findByCode(""))
    }

    /**
     * Verifica el comportamiento con strings que solo contienen espacios.
     */
    @Test
    fun `findByCode with blank string should return null`() {
        assertNull(CurrencyProvider.findByCode("   "))
    }

    /**
     * Verifica el comportamiento con caracteres especiales y espacios en blanco.
     */
    @Test
    fun `findByCode with special characters should return null`() {
        assertNull(CurrencyProvider.findByCode("U\$D"))
        assertNull(CurrencyProvider.findByCode("\nUSD\t"))
    }

    /**
     * Verifica que el sistema use USD por defecto cuando el locale del sistema no es válido
     * o no tiene una moneda asociada.
     */
    @Test
    fun `getAll should fallback to USD when locale is invalid`() {
        val originalLocale = Locale.getDefault()
        try {
            // Un locale sin país a menudo no tiene moneda
            Locale.setDefault(Locale.Builder().setLanguage("xx").setRegion("YY").build()) 
            val currencies = CurrencyProvider.getAll()
            
            // Debería caer en USD en SystemCurrencyProvider
            assertEquals("USD", currencies.first().code)
            assertEquals(0, currencies.first().id)
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    /**
     * Prueba de carga ligera: verifica que los cambios rápidos de locale
     * se reflejen correctamente en el proveedor.
     */
    @Test
    fun `stress test - multiple rapid calls with locale changes`() {
        val originalLocale = Locale.getDefault()
        try {
            repeat(100) {
                Locale.setDefault(Locale.UK)
                assertEquals("GBP", CurrencyProvider.getAll().first().code)
                
                Locale.setDefault(Locale.JAPAN)
                assertEquals("JPY", CurrencyProvider.getAll().first().code)
                
                Locale.setDefault(Locale.GERMANY)
                assertEquals("EUR", CurrencyProvider.getAll().first().code)
            }
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    /**
     * Simula una llamada desde Java pasando un valor nulo para verificar la robustez.
     */
    @Test
    fun `findByCode with null should throw or return null (simulating Java call)`() {
        val method = CurrencyProvider::class.java.getMethod("findByCode", String::class.java)
        try {
            val result = method.invoke(CurrencyProvider, null as String?)
            assertNull(result)
        } catch (e: Exception) {
            // Si lanza InvocationTargetException envolviendo un NullPointerException, 
            // significa que la API no es segura para nulos cuando se salta Kotlin
            assertTrue(e.cause is NullPointerException || e is NullPointerException)
        }
    }

    /**
     * Verifica el comportamiento con un locale específico (Tailandia).
     */
    @Test
    fun `test with exotic locale - Thailand`() {
        val originalLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale("th", "TH"))
            val currency = CurrencyProvider.getAll().first()
            assertEquals("THB", currency.code)
            assertTrue(currency.symbol.isNotEmpty())
        } finally {
            Locale.setDefault(originalLocale)
        }
    }
}
