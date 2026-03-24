<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
<script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
<script src="https://code.jquery.com/jquery-4.0.0.js"></script>

<style type="text/tailwindcss">
	@theme {
		--color-brand-50: #eff6ff;
		--color-brand-100: #dbeafe;
		--color-brand-500: #3b82f6;
		--color-brand-600: #2563eb;
		--color-brand-700: #1d4ed8;
		--color-point-500: #f59e0b;
	}

	@layer base {
		body { @apply bg-slate-50 text-slate-800; }
	}

	@layer components {
		/* 기존 공통 클래스 */
		.page-wrap { @apply max-w-6xl w-full mx-auto px-4 py-8; }
		.section-title { @apply text-2xl font-bold mb-2; }
		.section-desc { @apply text-sm text-slate-500 mb-6; }
		.content-card { @apply bg-white border border-slate-200 rounded-2xl shadow-sm; }
		.card-pad { @apply p-5 md:p-6; }
		.form-label { @apply block text-sm font-semibold mb-2 text-slate-700; }
		.btn-brand { @apply text-white bg-brand-600 hover:bg-brand-700 border-0; }
		.btn-soft-brand { @apply bg-brand-50 text-brand-700 border border-brand-100 hover:bg-brand-100; }
		.chip { @apply inline-flex items-center rounded-full px-3 py-1 text-xs font-semibold; }
		.chip-blue { @apply bg-blue-50 text-blue-700; }
		.chip-amber { @apply bg-amber-50 text-amber-700; }
		.chip-emerald { @apply bg-emerald-50 text-emerald-700; }
		
		
	}
</style>

<link rel="stylesheet" type="text/tailwindcss" href="/teamtwo/asset/css/main.css" />